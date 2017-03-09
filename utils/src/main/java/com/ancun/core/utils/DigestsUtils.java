/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.ancun.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;

import org.apache.commons.lang3.Validate;

/**
 * 散列工具类
 * 
 * <p>
 * create at 2016年1月22日 下午3:37:31
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version %I%, %G%
 *
 */
public class DigestsUtils {
	private static final String SHA256 = "SHA-256";
	private static final String SHA1 = "SHA-1";
	private static final String MD5 = "MD5";

	private static SecureRandom random = new SecureRandom();

	public static byte[] md5(byte[] input) {
		return digest(input, MD5, null, 1);
	}

	public static byte[] md5(byte[] input, byte[] salt) {
		return digest(input, MD5, salt, 1);
	}

	public static byte[] md5(byte[] input, byte[] salt, int iterations) {
		return digest(input, MD5, salt, iterations);
	}

	public static byte[] md5(InputStream input) throws IOException {
		return digest(input, MD5);
	}

	public static String md5Hex(byte[] input) {
		return bytes2Hex(digest(input, MD5, null, 1));
	}

	public static String md5Hex(byte[] input, byte[] salt) {
		return bytes2Hex(digest(input, MD5, salt, 1));
	}

	public static String md5Hex(byte[] input, byte[] salt, int iterations) {
		return bytes2Hex(digest(input, MD5, salt, 1));
	}

	public static String md5Hex(InputStream input) throws IOException {
		return bytes2Hex(digest(input, MD5));
	}

	/**
	 * 对输入字符串进行sha1散列.
	 */
	public static byte[] sha1(byte[] input) {
		return digest(input, SHA1, null, 1);
	}

	public static byte[] sha1(byte[] input, byte[] salt) {
		return digest(input, SHA1, salt, 1);
	}

	public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
		return digest(input, SHA1, salt, iterations);
	}

	/**
	 * 对文件进行sha1散列.
	 */
	public static byte[] sha1(InputStream input) throws IOException {
		return digest(input, SHA1);
	}

	/**
	 * 对输入字符串进行sha1散列.
	 */
	public static String sha1Hex(byte[] input) {
		return bytes2Hex(digest(input, SHA1, null, 1));
	}

	public static String sha1Hex(byte[] input, byte[] salt) {
		return bytes2Hex(digest(input, SHA1, salt, 1));
	}

	public static String sha1Hex(byte[] input, byte[] salt, int iterations) {
		return bytes2Hex(digest(input, SHA1, salt, iterations));
	}

	/**
	 * 对文件进行sha1散列.
	 */
	public static String sha1Hex(InputStream input) throws IOException {
		return bytes2Hex(digest(input, SHA1));
	}

	public static byte[] sha256(byte[] input) {
		return digest(input, SHA256, null, 1);
	}

	public static byte[] sha256(byte[] input, byte[] salt) {
		return digest(input, SHA256, salt, 1);
	}

	public static byte[] sha256(byte[] input, byte[] salt, int iterations) {
		return digest(input, SHA256, salt, iterations);
	}

	/**
	 * 对输入字符串进行sha1散列.
	 */
	public static String sha256Hex(byte[] input) {
		return bytes2Hex(digest(input, SHA256, null, 1));
	}

	public static String sha256Hex(byte[] input, byte[] salt) {
		return bytes2Hex(digest(input, SHA256, salt, 1));
	}

	public static String sha256Hex(byte[] input, byte[] salt, int iterations) {
		return bytes2Hex(digest(input, SHA256, salt, iterations));
	}

	/**
	 * 对文件进行sha1散列.
	 */
	public static String sha256Hex(InputStream input) throws IOException {
		return bytes2Hex(digest(input, SHA256));
	}

	/**
	 * 生成随机的Byte[]作为salt.
	 * 
	 * @param numBytes
	 *            byte数组的大小
	 */
	private static byte[] generateSalt(int numBytes) {
		Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", numBytes);

		byte[] bytes = new byte[numBytes];
		random.nextBytes(bytes);
		return bytes;
	}

	private static byte[] digest(InputStream input, String algorithm) throws IOException {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			int bufferLength = 8 * 1024;
			byte[] buffer = new byte[bufferLength];
			int read = input.read(buffer, 0, bufferLength);

			while (read > -1) {
				messageDigest.update(buffer, 0, read);
				read = input.read(buffer, 0, bufferLength);
			}

			return messageDigest.digest();
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 对字符串进行散列.
	 */
	private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
		try {
			MessageDigest digest = MessageDigest.getInstance(algorithm);

			if (salt != null) {
				digest.update(salt);
			}

			byte[] result = digest.digest(input);

			for (int i = 1; i < iterations; i++) {
				digest.reset();
				result = digest.digest(result);
			}
			return result;
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	private static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

}
