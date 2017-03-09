package com.ancun.core.utils;

import org.junit.Test;

public class RegionUtilsTest {

	@Test
	public void testGetParentCode() {
		System.out.println(RegionUtils.getParentCode("330102"));
		System.out.println(RegionUtils.getParentCode("330100"));
		System.out.println(RegionUtils.getParentCode("330000"));

	}

	@Test
	public void testGetFullParentCodePath() {
		System.out.println(RegionUtils.getCodeFullPath("330102"));
		System.out.println(RegionUtils.getCodeFullPath("330100"));
		System.out.println(RegionUtils.getCodeFullPath("330000"));
	}

	@Test
	public void testGetTopParentCode() {
		System.out.println(RegionUtils.getParentCodePrefix("330102"));
		System.out.println(RegionUtils.getParentCodePrefix("330100"));
		System.out.println(RegionUtils.getParentCodePrefix("330000"));

	}

}
