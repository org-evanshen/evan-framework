package com.ancun.core.utils;


import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ancun.core.utils.support.Demo;
import com.ancun.core.utils.support.DemoQuery;

public class JsonUtilsTest {

    @Test
    public void testBeanToJSON() {
        String json = null;
//        AncunApiResponse ancunApiResponse = AncunApiResponse.create();
//        List list = new ArrayList();
//
//        // ancunApiResponse.setList(list);
//        // ancunApiResponse.
//        String json = JsonUtils.beanToJSON(ancunApiResponse);
//
//        System.out.println(json);
//
//        AncunApiResponse ancunApiResponse1 = JsonUtils.jsonToBean(json, AncunApiResponse.class);
//        System.out.println(ancunApiResponse1);

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