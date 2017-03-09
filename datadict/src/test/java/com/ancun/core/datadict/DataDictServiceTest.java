package com.ancun.core.datadict;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ancun.core.model.DataDictionary;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({//
        "classpath*:sysconfig-bean.xml", //
        "classpath*:cache-bean.xml", //
        "classpath*:persistence-bean.xml", //
        "classpath*:datadict-bean.xml", //
        "classpath*:test-bean.xml"})
public class DataDictServiceTest {

    @Autowired
    private DataDictionaryService dataDictionaryService;

    @Test
    public void testGet() {
        List<DataDictionary> list = dataDictionaryService.getByGroup("sex");
        printList(list);

        list = dataDictionaryService.getByGroup("cardType");
        printList(list);

        DataDictionary o = dataDictionaryService.getByValue("education", "8");
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