package com.ancun.core.datadict.impl;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ancun.core.datadict.model.PubDataDictionaryEntity;
import com.ancun.core.datadict.model.PubDataDictionaryQuery;

public interface PubDataDictionaryMapper {
    /***/
    void insert(PubDataDictionaryEntity entity);

    /***/
    void update(PubDataDictionaryEntity entity);

    /***/
    List<PubDataDictionaryEntity> queryList(PubDataDictionaryQuery query);

    /***/
    int queryCount(PubDataDictionaryQuery query);

    PubDataDictionaryEntity load(@Param("dictGroup") String dictGroup, @Param("dictValue") String dictValue);

    void updateStatus(@Param("dictGroup") String dictGroup, @Param("dictValue") String dictValue,
                      @Param("status") int status);

    void delete(@Param("dictGroup") String dictGroup, @Param("dictValue") String dictValue);
}