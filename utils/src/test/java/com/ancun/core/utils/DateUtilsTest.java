package com.ancun.core.utils;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DateUtilsTest {

	@Test
	public void testGetCurrentDate() {
		Date today = DateUtils.getCurrentDate();
		System.out.println(today);
	}

	@Test
	public void testGetCurrentCalendar() {
		Calendar today = DateUtils.getCurrentCalendar();
		System.out.println(today);
	}

	@Test
	public void testGetCalendar() {
		
	}
}