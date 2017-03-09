package com.ancun.core.datadict;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.ancun.core.datadict.impl.PubDataDictionaryMapper;
import com.ancun.core.datadict.model.PubDataDictionaryEntity;
import com.ancun.core.datadict.model.PubDataDictionaryQuery;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({//
        "classpath*:sysconfig-bean.xml", //
        "classpath*:cache-bean.xml", //
        "classpath*:persistence-bean.xml", //
        "classpath*:datadict-bean.xml", //
        "classpath*:test-bean.xml"})

@TransactionConfiguration(defaultRollback = true)
public class DataDictMapperTest {

    @Autowired
    private PubDataDictionaryMapper pubDataDictionaryMapper;

    @Test
    public void testInsert() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdate() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdateStatusStringSerializable() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteString() {
        fail("Not yet implemented");
    }

    @Test
    public void testQueryList() {
        PubDataDictionaryQuery query = new PubDataDictionaryQuery();
        query.setDictGroup("cardType");
        List<PubDataDictionaryEntity> list = pubDataDictionaryMapper.queryList(query);

        System.out.println(list);
    }

    @Test
    public void testQueryCount() {
        fail("Not yet implemented");
    }

    @Test
    public void testLoadStringString() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdateStatusStringStringInt() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteStringString() {
        fail("Not yet implemented");
    }

}
