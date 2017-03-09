package com.ancun.core.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.fail;

public class HmacSha1UtilsTest {

	@Test
	public void testSignatureStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testSignatureStringStringString() throws InvalidKeyException, NoSuchAlgorithmException,
			IllegalStateException, UnsupportedEncodingException {
		byte[] signTemp = HmacSha1Utils.signature(DigestUtils.md5Hex("{\"account\":\"18368013123\"}").toLowerCase(),
				"cdf4a007e2b02a0c49fc9b7ccfbb8a10c644f635e1765dcf2a7ab794ddc7edac", "UTF-8");

		String sign = Base64Util.encode(signTemp);
		String signFianl = UrlEncodeUtil.encode(sign);

		String sign1 = Base64.encodeBase64String(signTemp);
		String sign2 = Base64.encodeBase64URLSafeString(signTemp);

		System.out.println(sign);
		System.out.println(sign1);
		System.out.println(sign2);
		System.out.println(signFianl);

	}
	
	@Test
	public void testSign(){
		String sign = HmacSha1Utils.sign("{\"account\":\"18368013123\"}",DigestUtils.sha256Hex("123456"));
		System.out.println(sign);
	}
}
