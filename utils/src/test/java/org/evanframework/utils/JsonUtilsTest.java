package org.evanframework.utils;


import org.evanframework.utils.support.Demo;
import org.evanframework.utils.support.DemoQuery;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JsonUtilsTest {

    @Test
    public void testBeanToJSON() {
        String json = null;
        DemoQuery demoQuery = new DemoQuery();
        demoQuery.setPageSize(4);

        json = JsonUtils.beanToJSON(demoQuery);
        System.out.println(json);

        DemoQuery demoQuery1 = JsonUtils.jsonToBean(json, DemoQuery.class);
        System.out.println(demoQuery1);

        Demo demo = new Demo();
        demo.setId(1L);
        //demo.setStatusEnum(EnumPublishStatus.PUBLISHED);
        json = JsonUtils.beanToJSON(demo);
        System.out.println(json);

        Demo demo1 = JsonUtils.jsonToBean(json, Demo.class);
        System.out.println(demo1);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", null);
        json = JsonUtils.beanToJSON(map);
        System.out.println(json);
    }

    // @Test
    // public void testJSONToBean() {
    // String json = "{\"b\":2,\"a\":1}";
    // Map map = (Map) JsonUtils.jsonToBean(json, HashMap.class);
    //
    // System.out.println(map);
    // }

    @Test
    public void testArrayToJSON() {

    }

    @Test
    public void testJsonToBean() {
        String str = "{\"itemKey\":\"I-0051002\",\"recordNo\":\"NQLVCVQY8NL1F1TMNTQC\",\"flowNo\":\"X-0163003\",\"pageSize\":null,\"pageNo\":null,\"md5\":\"d751713988987e9331980363e24189ce\",\"clientUser\":null,\"enterprise\":null,\"list\":[],\"data\":[]}";

    }
}