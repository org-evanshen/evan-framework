package org.evanframework.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class SecurityTest {
	@Test
	public void testBase64() {
		String plaintext = "1";

		String base64 = Base64.encodeBase64String(plaintext.getBytes());

		System.out.println(base64);
	}

	@Test
	public void testDigestUtils() {
		String plaintext = "czw";

		String md5 = DigestUtils.md5Hex(plaintext);

		String sha = DigestUtils.sha1Hex(plaintext);

		System.out.println(md5);
		System.out.println(sha);
	}
}
