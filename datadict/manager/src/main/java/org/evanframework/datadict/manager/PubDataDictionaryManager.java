package org.evanframework.datadict.manager;

import org.evanframework.datadict.DatadictionaryProxy;
import org.evanframework.datadict.cache.DataDictionaryCacheForData;
import org.evanframework.datadict.cache.DataDictionaryCacheForName;
import org.evanframework.datadict.dto.DataDictionary;
import org.evanframework.datadict.dto.DataDictionaryList;
import org.evanframework.datadict.manager.mapper.PubDataDictionaryMapper;
import org.evanframework.datadict.manager.model.PubDataDictionary;
import org.evanframework.datadict.manager.model.PubDataDictionaryColumns;
import org.evanframework.datadict.manager.model.PubDataDictionaryQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典管理类<p>
 * 实现interface DatadictionaryProxy，给其他模块提供服务
 *
 * @author shen.wei
 */
@Repository
public class PubDataDictionaryManager implements DatadictionaryProxy {
    private static final String DICT_DB_ROOT_VALUE = "root";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PubDataDictionaryMapper pubDataDictionaryMapper;
    @Autowired
    private DataDictionaryCacheForData dataDictionaryCacheForData;
    @Autowired
    private DataDictionaryCacheForName dataDictionaryCacheForName;

    public DataDictionaryList getForList(String group, boolean isIncludeDeleted) {
        return getForList(group, null, isIncludeDeleted);
    }

    public DataDictionaryList getForList(String group, String parentValue, boolean isIncludeDeleted) {
        PubDataDictionaryQuery query = new PubDataDictionaryQuery();
        query.setDictGroup(group);
        query.setParentValue(parentValue);
        query.setIncludeDeleted(isIncludeDeleted);

        List<PubDataDictionary> pdds = pubDataDictionaryMapper.queryList(query);
        DataDictionaryList ddl = new DataDictionaryList(pdds.size());
        for (PubDataDictionary e : pdds) {
            ddl.add(e.toDTO());
        }
        return ddl;
    }

    public Map<String, DataDictionary> getForMap(String group) {
        PubDataDictionaryQuery query = new PubDataDictionaryQuery();
        query.setDictGroup(group);
        List<PubDataDictionary> list = pubDataDictionaryMapper.queryList(query);

        Map<String, DataDictionary> map = new HashMap<String, DataDictionary>();
        for (PubDataDictionary o : list) {
            map.put(o.getDictValue(), o.toDTO());
        }
        return map;
    }

    /**
     * 获取数据字典最大排序号
     *
     * @param group
     * @param parentValue
     * @return
     */
    public int getMaxSortNum(String group, String parentValue) {
        String sql = "select max(sort_num) as max_sort_num from pub_data_dictionary t where t.dict_group=? and t.parent_value=? ";
        return jdbcTemplate.queryForObject(sql, new Object[]{group, parentValue},
                new RowMapper<Integer>() {
                    @Override
                    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Object o = rs.getObject("max_sort_num");
                        if (o == null) {
                            return 0;
                        } else {
                            return Integer.valueOf(o.toString());
                        }
                    }
                });
    }

    /**
     * 判断字典排序号是否存在
     *
     * @param sortNum
     * @param group
     * @param parentValue
     * @return
     */
    public boolean isExistsBySortNum(int sortNum, String group, String parentValue) {
        String sql = "select count(1) as c from pub_data_dictionary where dict_group=? and  parent_value=? and sort_num=?";
        int c = jdbcTemplate.queryForObject(sql, new RowMapper<Integer>() {
            @Override
            public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getInt("c");
            }
        }, sql, group, parentValue, sortNum);
        return c > 0;
    }

    /**
     * 增加排序号 从排序号大于startSortNum(含)的字典的排序增加1<p>
     * update pub_data_dictionary a set a.sort_num=a.sort_num+1 where a.dict_group=? and a.parent_value=? and a.sort_num>=?
     *
     * @param startSortNum
     * @param group
     * @param parentValue
     */
    public void increaseSortNum(int startSortNum, String group, String parentValue) {
        String sql = "update pub_data_dictionary a set a.sort_num=a.sort_num+1 where a.dict_group=? and a.parent_value=? and a.sort_num>=?";
        jdbcTemplate.update(sql, group, parentValue, startSortNum);
    }

    /**
     * 增加排序号，将排序号从startSortNum(含)到endSortNum(含)的字典的的排序号增加1
     * <p>
     * update pub_data_dictionary a set a.sort_num=a.sort_num+1 where a.dict_group=? and a.parent_value=? and a.sort_num>=? and a.sort_num<?
     *
     * @param startSortNum
     * @param endSortNum
     * @param group
     * @param parentValue
     */
    public void increaseSortNum(int startSortNum, int endSortNum, String group, String parentValue) {
        String sql = "update pub_data_dictionary a set a.sort_num=a.sort_num+1 where a.dict_group=? and a.parent_value=? and a.sort_num>=? and a.sort_num<? ";
        jdbcTemplate.update(sql, group, parentValue, startSortNum, endSortNum);
    }

    /**
     * 减少排序号，将排序号从startSortNum(含)到endSortNum(含)的字典的的排序号减1，startSortNum、endSortNum从大到小
     * <p>
     * update pub_data_dictionary a set a.sort_num=a.sort_num-1 where a.dict_group=? and a.parent_value=? and a.sort_num<=? and a.sort_num>?
     *
     * @param startSortNum
     * @param endSortNum
     * @param group
     * @param parentValue
     */
    public void decreaseSortNum(int startSortNum, int endSortNum, String group, String parentValue) {
        String sql = "update pub_data_dictionary a set a.sort_num=a.sort_num-1 where a.dict_group=? and a.parent_value=? and a.sort_num<=? and a.sort_num>? ";
        jdbcTemplate.update(sql, group, parentValue, startSortNum, endSortNum);
    }

    /**
     * 取数据库中所有根节点元素
     * <p/>
     * author: <a href="mailto:277469513@qq.com">ShenWei</a><br>
     * version: 2011-3-14 上午10:59:25 <br>
     */
    public List<PubDataDictionary> getGroup() {
        PubDataDictionaryQuery query = new PubDataDictionaryQuery();

        query.setParentValue(DICT_DB_ROOT_VALUE);
        query.setColumns(PubDataDictionaryColumns.DICT_TEXT.getColumn(),//
                PubDataDictionaryColumns.DICT_VALUE.getColumn(),//
                PubDataDictionaryColumns.SUB_SYSTEM.getColumn()//
        );

        return pubDataDictionaryMapper.queryList(query);
    }


    public PubDataDictionary load(String dictGroup, String dictValue) {
        return pubDataDictionaryMapper.load(dictGroup, dictValue);
    }

    public List<PubDataDictionary> queryList(PubDataDictionaryQuery query) {
        return pubDataDictionaryMapper.queryList(query);
    }


    public void insert(PubDataDictionary dataDictionary) {
        Assert.notNull(dataDictionary.getDictGroup(), "数据字典分组不能为空");
        pubDataDictionaryMapper.insert(dataDictionary);
        dataDictionaryCacheForData.remove(dataDictionary.getDictGroup());
    }

    public void update(PubDataDictionary dataDictionary) {
        Assert.notNull(dataDictionary.getDictGroup(), "数据字典分组不能为空");
        Assert.notNull(dataDictionary.getDictValue(), "数据字典值不能为空");

        pubDataDictionaryMapper.update(dataDictionary);
        dataDictionaryCacheForData.remove(dataDictionary.getDictGroup());
        dataDictionaryCacheForName.remove(dataDictionary.getDictValue());
    }

    public void updateStatus(String dictGroup, String dictValue, int status) {
        dataDictionaryCacheForData.remove(dictGroup);
        pubDataDictionaryMapper.updateStatus(dictGroup, dictValue, status);
    }
}