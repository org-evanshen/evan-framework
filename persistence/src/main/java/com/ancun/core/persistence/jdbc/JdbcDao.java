package com.ancun.core.persistence.jdbc;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.ancun.core.model.PageResult;
import com.ancun.core.query.QueryParam;

/**
 * JdbcDao
 * <p/>
 *
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2013-5-4 上午1:26:02
 */
public interface JdbcDao {

    /**
     * <p>
     * <a href="mailto:shenwei@ancun.com">ShenWei</a> create at 2013-9-30
     * 上午10:11:02
     * </p>
     *
     * @param sqlData
     * @param sqlCount
     * @param query
     * @param rowMapper
     * @param params
     */
    <T> PageResult<T> queryPage(String sqlData, String sqlCount, QueryParam query, RowMapper<T> rowMapper,
                                Object... params);

    /**
     * <p>
     * <a href="mailto:shenwei@ancun.com">ShenWei</a> create at 2013-9-30
     * 上午10:11:07
     * </p>
     *
     * @param sql
     * @param query
     * @param rowMapper
     * @param params
     */
    <T> List<T> queryList(String sql, QueryParam query, RowMapper<T> rowMapper, Object... params);

    //PageResult<Map<String, Object>> queryPageForMap(JdbcPageSqlWrapper jdbcPageSqlWrapper,
    //RowMapper<Map<String, Object>> rowMapper);

    //<T> PageResult<T> queryPageForObject(JdbcPageSqlWrapper jdbcPageSqlWrapper, RowMapper<T> rowMapper);

    /**
     * <p/>
     * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
     * version: 2012-11-19 上午10:04:47 <br>
     */
    String getSysDate();

    /**
     * <p/>
     * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
     * version: 2012-11-19 上午10:04:53 <br>
     */
    String getToDate();

    /**
     * 获取字符串连接符号
     * <p/>
     * author: <a href="mailto:shenwei@ancun.com">ShenWei</a><br>
     * version: 2012-11-19 上午10:07:13 <br>
     */
    String getStringContectSymbol();
}
