package org.evanframework.datadict.service;


import org.evanframework.datadict.dto.DataDictionary;
import org.evanframework.datadict.service.support.SpringBeansConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBeansConfig.class)
public class DataDictServiceTest {

    @Autowired
    private DataDictionaryService dataDictionaryService;

    @Test
    public void testGet() {
        List<DataDictionary> list = dataDictionaryService.getForList("sex");
        printList(list);

        list = dataDictionaryService.getForList("cardType");
        printList(list);

        DataDictionary o = dataDictionaryService.getForObject("education", "8");
        printObject(o);
    }

    private void printList(List<DataDictionary> list) {
        if (list != null) {
            for (DataDictionary o : list) {
                printObject(o);
            }
        }
    }

    private void printObject(DataDictionary o) {
        if (o != null) {
            System.out.println(o.getText() + ":" + o.getValue());
        }
    }
}