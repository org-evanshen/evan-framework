package com.ancun.core.datadict.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.ancun.core.datadict.model.PubDataDictionaryColumns;
import com.ancun.core.datadict.model.PubDataDictionaryEntity;
import com.ancun.core.datadict.model.PubDataDictionaryQuery;

/**
 *
 */
public class PubDataDictionaryDao {
    private static final String DICT_DB_ROOT_VALUE = "root";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PubDataDictionaryMapper pubDataDictionaryMapper;

    /**
     * 取数据库中所有根节点元素
     * <p/>
     * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
     * version: 2011-3-14 上午10:59:25 <br>
     */
    public List<PubDataDictionaryEntity> getGroup() {
        PubDataDictionaryQuery query = new PubDataDictionaryQuery();

        query.setParentValue(DICT_DB_ROOT_VALUE);
        query.setColumns(PubDataDictionaryColumns.DICT_TEXT.getColumn(),//
                PubDataDictionaryColumns.DICT_VALUE.getColumn(),//
                PubDataDictionaryColumns.SUB_SYSTEM.getColumn()//
        );

        return pubDataDictionaryMapper.queryList(query);
    }

    /**
     * 取节点
     */
    public List<PubDataDictionaryEntity> getByGroup(String group, boolean isIncludeDeleted) {
        return getByGroupAndParentValue(group, null, isIncludeDeleted);
    }

    public List<PubDataDictionaryEntity> getByGroupAndParentValue(String group, String parentValue,
                                                                  boolean isIncludeDeleted) {
        PubDataDictionaryQuery query = new PubDataDictionaryQuery();
        query.setDictGroup(group);
        query.setParentValue(parentValue);
        query.setIncludeDeleted(isIncludeDeleted);

        return pubDataDictionaryMapper.queryList(query);
    }

    public Map<String, PubDataDictionaryEntity> getByGroupForMap(String group) {
        PubDataDictionaryQuery query = new PubDataDictionaryQuery();
        query.setDictGroup(group);
        List<PubDataDictionaryEntity> list = pubDataDictionaryMapper.queryList(query);

        Map<String, PubDataDictionaryEntity> map = new HashMap<String, PubDataDictionaryEntity>();
        for (PubDataDictionaryEntity o : list) {
            map.put(o.getDictValue(), o);
        }
        return map;
    }

    public int getMaxSortNum(String group, String parentValue) {
        String sql = "select max(SORT_NUM) as MAX_SORT_NUM from PUB_DATA_DICTIONARY t where t.DICT_GROUP=? and t.PARENT_VALUE=? ";

        return jdbcTemplate.queryForObject(sql, new Object[]{group, parentValue},
                new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Object o = rs.getObject("MAX_SORT_NUM");
                        if (o == null) {
                            return 0;
                        } else {
                            return Integer.valueOf(o.toString());
                        }
                    }
                });
    }

    public boolean isExistsBySortNum(int sortNum, String group, String parentValue) {
        String sql = "select count(1) as c from PUB_DATA_DICTIONARY where DICT_GROUP=? and  PARENT_VALUE=? and SORT_NUM=?";
        int c = jdbcTemplate.queryForObject(sql, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                // TODO Auto-generated method stub
                return rs.getInt("c");
            }
        }, sql, group, parentValue, sortNum);
        return c > 0;
    }

    public void increaseSortNum(int startSortNum, String group, String parentValue) {
        String sql = "update PUB_DATA_DICTIONARY a set a.SORT_NUM=a.SORT_NUM+1 where a.DICT_GROUP=? and a.PARENT_VALUE=? and a.SORT_NUM>=?";
        jdbcTemplate.update(sql, group, parentValue, startSortNum);
    }

    public void increaseSortNum(int startSortNum, int endSortNum, String group, String parentValue) {
        String sql = "update PUB_DATA_DICTIONARY a set a.SORT_NUM=a.SORT_NUM+1 where a.DICT_GROUP=? and a.PARENT_VALUE=? and a.SORT_NUM>=? and a.SORT_NUM<? ";
        jdbcTemplate.update(sql, group, parentValue, startSortNum, endSortNum);
    }

    public void decreaseSortNum(int startSortNum, int endSortNum, String group, String parentValue) {
        String sql = "update PUB_DATA_DICTIONARY a set a.SORT_NUM=a.SORT_NUM-1 where a.DICT_GROUP=? and a.PARENT_VALUE=? and a.SORT_NUM<=? and a.SORT_NUM>? ";
        jdbcTemplate.update(sql, group, parentValue, startSortNum, endSortNum);
    }
}