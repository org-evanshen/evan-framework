package org.evanframework.utils;

import static org.junit.Assert.fail;

import org.junit.Test;

public class ValidateUtilsTest {

	@Test
	public void testIsInteger() {
		fail("Not yet implemented");
	}

	@Test
	public void testRegexValidate() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckEmail() {
		boolean a = ValidateUtils.checkEmail("sun_shen_1977@163.com");
		System.out.println("sun_shen_1977@163.com:" + a);
		
		a = ValidateUtils.checkEmail("123");
		System.out.println("123:" + a);
	}

	@Test
	public void testCheckMobile() {
		fail("Not yet implemented");
	}

}
