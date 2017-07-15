package org.evanframework.datadict.manager.mapper;


import org.evanframework.datadict.manager.model.PubDataDictionary;
import org.evanframework.datadict.manager.model.PubDataDictionaryQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PubDataDictionaryMapper {

    void insert(PubDataDictionary entity);

    void update(PubDataDictionary entity);

    List<PubDataDictionary> queryList(PubDataDictionaryQuery query);

    int queryCount(PubDataDictionaryQuery query);

    PubDataDictionary load(@Param("dictGroup") String dictGroup, @Param("dictValue") String dictValue);

    void updateStatus(@Param("dictGroup") String dictGroup, @Param("dictValue") String dictValue, @Param("status") int status);

    void delete(@Param("dictGroup") String dictGroup, @Param("dictValue") String dictValue);
}