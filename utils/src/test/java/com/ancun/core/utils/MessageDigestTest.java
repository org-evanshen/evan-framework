package com.ancun.core.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class MessageDigestTest {

	private final static Logger log = LoggerFactory.getLogger(MessageDigestTest.class);

	private MessageDigest mdMd5;
	private MessageDigest mdSha1;
	private PasswordEncoder passwordEncoderSha;
	private PasswordEncoder passwordEncoderMD5;

	@Before
	public void before() throws NoSuchAlgorithmException {
		mdMd5 = MessageDigest.getInstance("MD5");
		mdSha1 = MessageDigest.getInstance("SHA-1");
		passwordEncoderSha = new ShaPasswordEncoder();
		passwordEncoderMD5 = new Md5PasswordEncoder();
	}

	@Test
	public void test() throws NoSuchAlgorithmException {
		String plantText = "100000";

		mdMd5.update(plantText.getBytes());
		byte[] digestByte = mdMd5.digest();
		String digest = Hex.encodeHexString(digestByte);
		log.info(digest);
		
		log.info(passwordEncoderMD5.encodePassword(plantText, null));

		mdSha1.update(plantText.getBytes());
		digestByte = mdSha1.digest();
		digest = Hex.encodeHexString(digestByte);

		log.info(digest);
		
		log.info(passwordEncoderSha.encodePassword(plantText, 1234));

	}

}
