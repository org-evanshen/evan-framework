package com.ancun.core.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class DigestUtilTest {
	@Test
	public void test() {
		String plain = "51cunzheng";

		System.out.println("plain:" + plain);
		
		String md5 = DigestUtils.md5Hex(plain);			
		System.out.println("md5:" + md5);
		
		String sha = DigestUtils.shaHex(plain);
		System.out.println("sha:" + sha);
		
		String sha256 = DigestUtils.sha256Hex(plain);
		System.out.println("sha256:" + sha256);
		
		String sha384 = DigestUtils.sha384Hex(plain);
		System.out.println("sha384:" + sha384);

	}
}
