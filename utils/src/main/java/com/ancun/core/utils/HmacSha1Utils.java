package com.ancun.core.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class HmacSha1Utils {
	private static final String HMAC_SHA1 = "HmacSHA1";
	private static final String CHARSET_NAME = "UTF-8";

	@Deprecated
	public static byte[] signature(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException,
			IllegalStateException, UnsupportedEncodingException {
		return signature(data, key, CHARSET_NAME);
	}

	@Deprecated
	public static byte[] signature(String data, String key, String charsetName) throws NoSuchAlgorithmException,
			InvalidKeyException, IllegalStateException, UnsupportedEncodingException {
		byte[] keyBytes = key.getBytes();
		SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1);
		Mac mac = Mac.getInstance(HMAC_SHA1);
		mac.init(signingKey);
		return mac.doFinal(data.getBytes(charsetName));
	}

	public static String sign(String data, String key) {
		String dataMd5 = DigestUtils.md5Hex(data).toLowerCase();
		
		byte[] keyBytes = key.getBytes();
		SecretKeySpec signingKey = new SecretKeySpec(keyBytes, HMAC_SHA1);
		String sign = null;
		byte[] result = null;
		try {
			Mac mac = Mac.getInstance(HMAC_SHA1);
			mac.init(signingKey);
			result = mac.doFinal(dataMd5.getBytes(CHARSET_NAME));
		} catch (NoSuchAlgorithmException e) {
			throw new UnsupportedOperationException("Algorithm[" + HMAC_SHA1 + "] is not not support", e);
		} catch (InvalidKeyException e) {
			throw new UnsupportedOperationException("SecretKeySpec [" + signingKey + "] is invalid", e);
		} catch (IllegalStateException e) {
			throw new UnsupportedOperationException(e);
		} catch (UnsupportedEncodingException e) {
			throw new UnsupportedOperationException("Encoding [" + CHARSET_NAME + "] is invalid", e);
		}
		if (result != null) {
			sign = UrlEncodeUtil.encode(Base64.encodeBase64String(result));
		}
		return sign;
	}

}
