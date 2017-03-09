package com.ancun.core.utils;

import org.junit.Test;

public class RandomDataTest {

	@Test
	public void test() {
		System.out.println(RandomData.randomId());
		System.out.println(RandomData.randomInt(999999));
		System.out.println(RandomData.randomInt(9999));
	}
}
