package com.ancun.core.persistence.jdbc;

import java.util.List;

import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.ancun.core.model.PageResult;
import com.ancun.core.query.QueryParam;

/**
 * AbstractJdbcDao
 * <p/>
 *
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2013-5-4 上午1:26:17
 */

public abstract class AbstractJdbcDao implements JdbcDao {
    private JdbcTemplate jdbcTemplate;

    public AbstractJdbcDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // public PageResult<Map<String, Object>> queryPageForMap(JdbcPageSqlWrapper
    // jdbcPageSqlWrapper,
    // RowMapper<Map<String, Object>> rowMapper) {
    // return queryForPageInner(jdbcPageSqlWrapper.getQuery(),
    // jdbcPageSqlWrapper.getSqlData(),
    // jdbcPageSqlWrapper.getSqlCount(), jdbcPageSqlWrapper.getParams(),
    // rowMapper,
    // new PageDataGettorForMap());
    // }

    // public <T> PageResult<T> queryPageForObject(JdbcPageSqlWrapper
    // jdbcPageSqlWrapper, RowMapper<T> rowMapper) {
    // return queryPageForObject(jdbcPageSqlWrapper.getSqlData(),
    // jdbcPageSqlWrapper.getSqlCount(),
    // jdbcPageSqlWrapper.getQuery(), rowMapper,
    // jdbcPageSqlWrapper.getParams());
    // }

    public <T> PageResult<T> queryPage(String sqlData, String sqlCount, QueryParam query,
                                       RowMapper<T> rowMapper, Object... params) {
        if (rowMapper == null) {
            return queryForPageInner(query, sqlData, sqlCount, params, rowMapper,
                    new PageDataGettorForMap());
        } else {
            return queryForPageInner(query, sqlData, sqlCount, params, rowMapper,
                    new PageDataGettorForClass());
        }
    }

    public <T> List<T> queryList(String sql, QueryParam query, RowMapper<T> rowMapper,
                                 Object... params) {
        sql = getSqlPage(query, sql);

        return jdbcTemplate.query(sql, params, rowMapper);
    }

    /**
     * 获取分页语句
     * <p/>
     * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
     * version: 2013-5-4 上午8:43:28 <br>
     *
     * @param query
     * @param sqlData
     */
    protected abstract String getSqlPage(QueryParam query, String sqlData);

    private <T> PageResult<T> queryForPageInner(QueryParam query, String sqlData, String sqlCount,
                                                Object[] params, RowMapper<T> rowMapper, PageDataGettor pageDataGettor) {

        Long recordCount = jdbcTemplate.queryForObject(sqlCount, Long.class, params);

        String sqlPage = getSqlPage(query, sqlData);

        List<T> data = pageDataGettor.getPageData(query, sqlPage, params, rowMapper);

        PageResult<T> pageResult = PageResult.create(query, data, recordCount);

        return pageResult;
    }

    public interface PageDataGettor {

        public abstract <T> List<T> getPageData(QueryParam query, String sqlData, Object[] objs,
                                                RowMapper<T> rowMapper);

    }

    public class PageDataGettorForMap implements PageDataGettor {

        @SuppressWarnings("unchecked")
        @Override
        public <T> List<T> getPageData(QueryParam query, String sqlData, Object[] objs,
                                       RowMapper<T> rowMapper) {
            if (rowMapper == null) {
                rowMapper = (RowMapper<T>) new ColumnMapRowMapper();
            }
            List<T> list = jdbcTemplate.query(sqlData.toString(), objs, rowMapper);
            return list;
        }
    }

    public class PageDataGettorForClass implements PageDataGettor {
        @Override
        public <T> List<T> getPageData(QueryParam query, String sqlData, Object[] objs,
                                       RowMapper<T> rowMapper) {
            List<T> list = jdbcTemplate.query(sqlData.toString(), objs, rowMapper);
            return list;
        }
    }
}
