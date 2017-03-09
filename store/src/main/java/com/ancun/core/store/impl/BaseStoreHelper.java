package com.ancun.core.store.impl;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ancun.core.store.StoreConstants;
import com.ancun.core.sysconfig.SysConfig;

/**
 * create at 2016/5/31
 *
 * @author <a href="mailto:taofei@ancun.com">Paul Yao</a>
 * @version %I%, %G%
 *
 */
public abstract class BaseStoreHelper {
    private static final Logger logger = LoggerFactory.getLogger(BaseStoreHelper.class);

    protected String DEFAULT_BUCKET_NAME = "ancun-platform";

    /**
     * 存储的bucket name
     */
    protected String bucketName;
    protected SysConfig sysConfig;
    protected String storeRootDir;

    protected void init(){
        storeRootDir = sysConfig.get(StoreConstants.STORE_DIR);
        if (StringUtils.isBlank(bucketName)) {
            bucketName = DEFAULT_BUCKET_NAME;
        }
    }

    public void setSysConfig(SysConfig sysConfig) {
        this.sysConfig = sysConfig;
        storeRootDir = sysConfig.get(StoreConstants.STORE_DIR);
    }

}
