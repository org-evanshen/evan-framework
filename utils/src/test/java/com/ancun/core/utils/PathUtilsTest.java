package com.ancun.core.utils;

import org.junit.Test;

public class PathUtilsTest {
	@Test
	public void testConcat() {
		System.out.println("PathUtils.concat(\"a\", \"c\",\"d\")="+PathUtils.concat("a", "c", "d"));
		System.out.println("PathUtils.concat(\"a/\", \"c\",\"/d\")="+PathUtils.concat("a/", "c", "/d"));
		System.out.println("PathUtils.concat(\"/a\", \"/c\",\"d/\")="+PathUtils.concat("/a", "/c", "d/"));
	}
}
