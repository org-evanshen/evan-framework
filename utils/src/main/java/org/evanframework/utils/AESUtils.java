package org.evanframework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * create at 2016/5/26
 *
 * @author <a href="mailto:taofei@">Paul Yao</a>
 * @version %I%, %G%
 * @see
 */
public class AESUtils {

    /**
     * 密钥算法
     */
    public static final String KEY_ALGORITHM = "AES";
    /**
     * 加密/解密算法 / 工作模式 / 填充方式
     * Java 6支持PKCS5Padding填充方式
     * Bouncy Castle支持PKCS7Padding填充方式
     */
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final Logger LOGGER = LoggerFactory.getLogger(AESUtils.class);

    /**
     * 转换密钥
     *
     * @param key 二进制密钥
     * @return Key 密钥
     * @throws Exception
     */
    private static Key toKey(byte[] key) {

        // 实例化AES密钥材料
        SecretKey secretKey = new SecretKeySpec(key, KEY_ALGORITHM);

        return secretKey;
    }

    /**
     * <p>
     * SecureRandom 实现完全隨操作系统本身的內部狀態，除非調用方在調用 getInstance 方法之後又調用了 setSeed 方法；
     * 该实现在 windows 上每次生成的 key 都相同，但是在 solaris 或部分 linux 系统上则不同。
     * </p>
     *
     * @param strKey
     * @return
     */
    public static SecretKey getKey(String strKey) {
        try {
            KeyGenerator _generator = KeyGenerator.getInstance("AES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(strKey.getBytes());
            _generator.init(128, secureRandom);
            return _generator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(" 初始化密钥出现异常 ");
        }
    }

    /**
     * 加密
     *
     * @param data     待加密数据
     * @param password 密钥
     * @return byte[] 加密数据
     * @throws Exception
     */
    public static String encrypt(String data, String password) {
        byte[] key = initKey(password);
        // 还原密钥
        Key k = toKey(key);

		/*
         * 实例化
		 * 使用PKCS7Padding填充方式，按如下方式实现
		 * Cipher.getInstance(CIPHER_ALGORITHM, "BC");
		 */
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        // 初始化，设置为加密模式
        try {
            cipher.init(Cipher.ENCRYPT_MODE, k);
        } catch (InvalidKeyException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        byte[] bytes = null;
        try {
            bytes = cipher.doFinal(data.getBytes());
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        // 执行操作
        return parseByte2HexStr(bytes);
    }

    /**
     * 解密
     *
     * @param data     待解密数据
     * @param password 密钥
     * @return byte[] 解密数据
     * @throws Exception
     */
    public static String decrypt(String data, String password) {
        byte[] key = initKey(password);

        // 还原密钥
        Key k = toKey(key);
        /*
         * 实例化
		 * 使用PKCS7Padding填充方式，按如下方式实现
		 * Cipher.getInstance(CIPHER_ALGORITHM, "BC");
		 */
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        // 初始化，设置为解密模式
        try {
            cipher.init(Cipher.DECRYPT_MODE, k);
        } catch (InvalidKeyException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        byte[] bytes;
        try {
            bytes = cipher.doFinal(parseHexStr2Byte(data));
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        // 执行操作
        return new String(bytes);
    }

    /**
     * 生成密钥 <br>
     *
     * @return byte[] 二进制密钥
     * @throws Exception
     */
    private static byte[] initKey(String password) {

        // 实例化
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        //防止linux下 随机生成key
        SecureRandom secureRandom = null;
        try {
            secureRandom = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        secureRandom.setSeed(password.getBytes());
        /*
         * AES 要求密钥长度为 128位、192位或 256位
		 */
        kg.init(128, secureRandom);

        // 生成秘密密钥
        SecretKey secretKey = kg.generateKey();

        // 获得密钥的二进制编码形式
        return secretKey.getEncoded();
    }

    /**
     * 将二进制转换成16进制
     *
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
}
