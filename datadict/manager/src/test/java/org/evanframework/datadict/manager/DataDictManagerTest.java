package org.evanframework.datadict.manager;

import org.evanframework.datadict.manager.model.PubDataDictionaryQuery;
import org.evanframework.datadict.dto.DataDictionaryList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by evan.shen on 2017/5/1.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBeansConfig.class)
public class DataDictManagerTest {
    @Autowired
    private PubDataDictionaryManager pubDataDictionaryManager;


    @Test
    public void testGetByGroup() {
        PubDataDictionaryQuery query = new PubDataDictionaryQuery();
        query.setDictGroup("cardType");
        DataDictionaryList list  = pubDataDictionaryManager.getByGroup("cardType",false);

        System.out.println("==============" + list);
    }
}
