package com.ancun.core.utils;


import java.util.Date;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ancun.core.utils.support.Demo;
import com.ancun.core.utils.support.DemoQuery;

import net.sf.cglib.beans.BeanCopier;

@SuppressWarnings("rawtypes")
public class BeanUtilsTest {

    private static final Logger log = LoggerFactory.getLogger(BeanUtilsTest.class);


    @Test
    public void testMap() {
        DemoQuery demoQuery = new DemoQuery();
        demoQuery.setFieldText("张三");
        demoQuery.setFieldProvince("330000");

        long begin = System.currentTimeMillis();
        log.info("Begin convert at " + begin);
        for (int i = 0; i < 1000000; i++) {
            BeanUtils.quickMap(demoQuery, Demo.class);
        }
        long end = System.currentTimeMillis();
        long span = end - begin;
        log.info("End convert as " + end + ", user time " + span);

    }

    @Test
    public void testBeanToMap() {
        DemoQuery demo = new DemoQuery();
        demo.setFieldDateFrom(new Date());
        Map map = BeanUtils.beanToMap(demo);
        System.out.println(map);

        // fail("Not yet implemented");
    }

    @Test
    public void testBeanToQuery() {
        DemoQuery demo = new DemoQuery();
        demo.setFieldDateFrom(new Date());
        String queryString = BeanUtils.beanToQueryString(demo, "yyyy-MM-dd hh:mm:ss");
        System.out.println(queryString);

        // fail("Not yet implemented");
    }

    @Test
    public void testGetObjectNameObject() {
        DemoQuery demo = new DemoQuery();
        demo.setFieldDateFrom(new Date());
        System.out.println(BeanUtils.getClassName(demo));
    }

    @Test
    public void testGetObjectNameClass() {
        System.out.println(BeanUtils.getObjectName(DemoQuery.class));
    }

    @Test
    public void testCopyProperties() {
        DemoQuery demoQuery = new DemoQuery();
        demoQuery.setFieldText("张三");
        demoQuery.setFieldProvince("330000");

        Demo demoDto = new Demo();
        long begin = System.currentTimeMillis();
        log.info("BeanUtils.copyProperties Begin convert at " + begin);
        for (int i = 0; i < 1000000; i++) {
            org.springframework.beans.BeanUtils.copyProperties(demoQuery, demoDto);
        }
        long end = System.currentTimeMillis();
        long span = end - begin;
        log.info("End convert as " + end + ", user time " + span);

        log.info("BeanUtils.copyProperties Begin convert at " + begin);
        BeanCopier beanCopier = BeanCopier.create(DemoQuery.class, demoDto.getClass(), false);
        begin = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            beanCopier.copy(demoQuery, demoDto, null);
        }
        end = System.currentTimeMillis();
        span = end - begin;
        log.info("End convert as " + end + ", user time " + span);

    }
    //	@Test
    //	public void testInvoke() throws Exception {
    //		QueryParamers queryParamers = new QueryParamers();
    //		HsBeanUtils.invoke(queryParamers, "setSortExpression", "aaa");
    //		HsBeanUtils.setProperty(queryParamers, "sortExpression", "bbbb");
    //		System.out.println(queryParamers.getSortExpression());
    //	}
}
