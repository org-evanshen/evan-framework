package org.evanframework.datadict.service;


import org.evanframework.datadict.dto.DataDictionary;
import org.evanframework.datadict.dto.DataDictionaryList;
import org.evanframework.datadict.service.support.SpringBeansConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBeansConfig.class)
public class DataDictServiceTest {

    @Autowired
    private DataDictionaryService dataDictionaryService;

    @Test
    public void testGetForList() {
        DataDictionaryList list = dataDictionaryService.getForList("sex");
        System.out.println("=========== testGetForList sex:" + list);

        list = dataDictionaryService.getForList("cardType");
        System.out.println("=========== testGetForList cardType:" + list);
    }

    @Test
    public void testGetForObject() {
        DataDictionary o = dataDictionaryService.getForObject("marriage", "1");
        System.out.println("=========== testGetForObject marriage 1 :" + o);

        o = dataDictionaryService.getForObject("encryptType", "7");
        System.out.println("=========== testGetForObject encryptType 7:" + o);
    }

    @Test
    public void getByForMap() {
        Map<String, DataDictionary> o = dataDictionaryService.getForMap("marriage");
        System.out.println("=========== getByForMap marriage 7:" + o);
    }
}