package com.ancun.core.sysconfig.impl;


import java.util.Set;

import org.jasypt.commons.CommonUtils;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.properties.PropertyValueEncryptionUtils;
import org.jasypt.util.text.TextEncryptor;
import org.springframework.core.io.Resource;

/**
 * create at 2016/1/12 下午2:53
 *
 * @author <a href="mailto:taofei@ancun.com">Paul Yao</a>
 * @version %I%, %G%
 *
 */
public class EncryptablePropertyPlaceholderConfigurer extends AppPropertyPlaceholderConfigurer {

    /*
     * Only one of these instances will be initialized, the other one will be
	 * null.
	 */
    private final StringEncryptor stringEncryptor;
    private final TextEncryptor textEncryptor;

    /**
     * <p>
     * Creates an <tt>EncryptablePropertyPlaceholderConfigurer</tt> instance
     * which will use the passed {@link StringEncryptor} object to decrypt
     * encrypted values.
     * </p>
     *
     * @param stringEncryptor the {@link StringEncryptor} to be used do decrypt values. It
     *                        can not be null.
     */
    public EncryptablePropertyPlaceholderConfigurer(final StringEncryptor stringEncryptor) {
        super();
        CommonUtils.validateNotNull(stringEncryptor, "Encryptor cannot be null");
        this.stringEncryptor = stringEncryptor;
        this.textEncryptor = null;
    }

    /**
     * <p>
     * Creates an <tt>EncryptablePropertyPlaceholderConfigurer</tt> instance which will use the
     * passed {@link TextEncryptor} object to decrypt encrypted values.
     * </p>
     *
     * @param textEncryptor the {@link TextEncryptor} to be used do decrypt values. It can
     *                      not be null.
     */
    public EncryptablePropertyPlaceholderConfigurer(final TextEncryptor textEncryptor) {
        super();
        CommonUtils.validateNotNull(textEncryptor, "Encryptor cannot be null");
        this.stringEncryptor = null;
        this.textEncryptor = textEncryptor;
    }


    @Override
    public void setLocations(Resource[] locations) {
        super.setLocations(locations);
        // 解密加密的属性
        Set<String> names = properties.stringPropertyNames();
        for(String name: names) {
            Object value = properties.get(name);
            if(value != null) {
                properties.put(name, convertPropertyValue((String)value));
            }
        }
    }

    /*
         * (non-Javadoc)
         *
         *  org.springframework.beans.factory.sysconfig.PropertyResourceConfigurer#convertPropertyValue(java.lang.String)
         */
    @Override
    protected String convertPropertyValue(final String originalValue) {
        if (!PropertyValueEncryptionUtils.isEncryptedValue(originalValue)) {
            return originalValue;
        }
        if (this.stringEncryptor != null) {
            return PropertyValueEncryptionUtils.decrypt(originalValue,
                    this.stringEncryptor);

        }
        return PropertyValueEncryptionUtils.decrypt(originalValue, this.textEncryptor);
    }

    /*
     * (non-Javadoc)
     *
     * @since 1.8
     *  org.springframework.beans.factory.sysconfig.PropertyPlaceholderConfigurer#resolveSystemProperty(java.lang.String)
     */
    //@Override
    protected String resolveSystemProperty(final String key) {
        return convertPropertyValue(super.convertPropertyValue(key));
    }
}
