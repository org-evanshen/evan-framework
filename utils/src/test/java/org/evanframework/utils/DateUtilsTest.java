package org.evanframework.utils;

import java.util.Date;

import org.junit.Test;

public class DateUtilsTest {

    @Test
    public void testParse() {
        Date date = DateUtils.parse("2015-10-01");
        System.out.println(date);
        date = DateUtils.parse("2015-10-02");
        System.out.println(date);
        date = DateUtils.parse("2015/10/03", "yyyy/MM/dd");
        System.out.println(date);
        date = DateUtils.parse("20151004", "yyyyMMdd");
        System.out.println(date);
        date = DateUtils.parse("2015-10-05 23:45:22", DateUtils.FORMAT_LONG_STRING);
        System.out.println(date);
    }

    @Test
    public void testFormat() {
        Date date = new Date();
        String str = DateUtils.format(date);
        System.out.println(str);
        DateUtils.addDay(date,2);
        str = DateUtils.format(date);
        System.out.println(str);
        str = DateUtils.format(date, DateUtils.FORMAT_LONG_STRING);
        System.out.println(str);
        str = DateUtils.format(date,"yyyyMMdd");
        System.out.println(str);
    }
}