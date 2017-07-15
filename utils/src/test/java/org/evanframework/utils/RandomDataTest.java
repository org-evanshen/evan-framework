package org.evanframework.utils;

import org.junit.Test;

public class RandomDataTest {

	@Test
	public void test() {
		System.out.println(RandomDataUtils.randomId());
		System.out.println(RandomDataUtils.randomInt(999999));
		System.out.println(RandomDataUtils.randomInt(9999));
	}
}
