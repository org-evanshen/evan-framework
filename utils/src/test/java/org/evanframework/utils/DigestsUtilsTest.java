package org.evanframework.utils;

import org.junit.Test;

public class DigestsUtilsTest {
	@Test
	public void test() {
		String plain = "51cunzheng";

		System.out.println("plain:" + plain);

		String md5 = DigestsUtils.md5Hex(plain.getBytes());
		System.out.println("md5:" + md5);

		String sha = DigestsUtils.sha1Hex(plain.getBytes());
		System.out.println("sha:" + sha);

		String sha256 = DigestsUtils.sha256Hex(plain.getBytes());
		System.out.println("sha256:" + sha256);

	}
}
