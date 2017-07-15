package org.evanframework.utils;

import java.io.File;

public class PathTest {
	public static void main(String[] args) throws Exception {
		System.out.println(Thread.currentThread().getContextClassLoader()
				.getResource(""));

		System.out.println(PathTest.class.getClassLoader().getResource(""));

		System.out.println(ClassLoader.getSystemResource(""));
		System.out.println(PathTest.class.getResource(""));
		System.out.println(PathTest.class.getResource("/"));
		// Class鏂囦欢鎵€鍦ㄨ矾寰?
		System.out.println(new File("/").getAbsolutePath());
		System.out.println(System.getProperty("user.dir"));
	}
}
