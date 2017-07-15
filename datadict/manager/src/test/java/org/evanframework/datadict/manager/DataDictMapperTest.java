package org.evanframework.datadict.manager;

import org.evanframework.datadict.manager.mapper.PubDataDictionaryMapper;
import org.evanframework.datadict.manager.model.PubDataDictionary;
import org.evanframework.datadict.manager.model.PubDataDictionaryQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBeansConfig.class)
public class DataDictMapperTest {

    @Autowired
    private PubDataDictionaryMapper pubDataDictionaryMapper;

    @Test
    public void testInsert() {
        //fail("Not yet implemented");
    }

    @Test
    public void testUpdate() {
        //fail("Not yet implemented");
    }

    @Test
    public void testUpdateStatusStringSerializable() {
        //fail("Not yet implemented");
    }

    @Test
    public void testDeleteString() {
        //fail("Not yet implemented");
    }

    @Test
    public void testQueryList() {
        PubDataDictionaryQuery query = new PubDataDictionaryQuery();
        query.setDictGroup("cardType");
        List<PubDataDictionary> list = pubDataDictionaryMapper.queryList(query);

        System.out.println("==============" + list);
    }

    @Test
    public void testQueryCount() {
        //fail("Not yet implemented");
    }

    @Test
    public void testLoadStringString() {
        //fail("Not yet implemented");
    }

    @Test
    public void testUpdateStatusStringStringInt() {
        //fail("Not yet implemented");
    }

    @Test
    public void testDeleteStringString() {
        //fail("Not yet implemented");
    }

}
