package com.ancun.core.sysparameter.impl;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.ancun.core.persistence.jdbc.JdbcUtils;
import com.ancun.core.sysparameter.model.SysParameter;
import com.ancun.core.sysparameter.model.SysParameterQuery;

/**
 * 系统参数表
 */
public class SysParameterDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 添加对象
     */
    public void insert(SysParameter po) {
        String sql = "insert into SYS_PARAMETER(PARAM_KEY,PARAM_VALUE,PARAM_NAME,PARAM_VALUE_DEBUG)"
                + "values (?,?,?,?)";

        Object[] params = new Object[4];

        params[0] = po.getParamKey();
        params[1] = po.getParamValue();
        params[2] = po.getParamName();
        params[3] = po.getParamValueDebug();

        jdbcTemplate.update(sql, params);
    }

    /**
     * 根据主键修改
     */
    public void update(SysParameter po) {
        List<Serializable> listParams = new ArrayList<Serializable>();
        StringBuilder sqlCols = new StringBuilder(512);

        JdbcUtils.appandSql(sqlCols, listParams, po.getParamValue(), ",PARAM_VALUE=?");
        JdbcUtils.appandSql(sqlCols, listParams, po.getParamName(), ",PARAM_NAME=?");
        JdbcUtils.appandSql(sqlCols, listParams, po.getParamValueDebug(), ",PARAM_VALUE_DEBUG=?");

        if (sqlCols.length() > 0) {
            sqlCols.delete(0, 1);
            String sql = "update SYS_PARAMETER  set " + sqlCols + " where  PARAM_KEY=? ";
            listParams.add(po.getParamKey());
            jdbcTemplate.update(sql.toString(), listParams.toArray());
        }
    }

    /**
     * 根据主键获取单个对象
     */
    public SysParameter load(String paramKey) {
        String sql = " select * from SYS_PARAMETER where PARAM_KEY=?";

        return jdbcTemplate.query(sql.toString(), new Object[]{paramKey}, new PubParameterResultSet());
    }

    /**
     * 列表查询
     */
    public List<SysParameter> queryForList(SysParameterQuery query) {
        StringBuilder sql = new StringBuilder(256);
        sql.append("select PARAM_KEY,PARAM_VALUE,PARAM_NAME,PARAM_VALUE_DEBUG,IS_READONLY from SYS_PARAMETER");

        StringBuilder sqlWhere = new StringBuilder(256);

        List<Serializable> listParams = new ArrayList<Serializable>();

        if (query != null) {
            JdbcUtils.appandSql(sqlWhere, listParams, query.getParamKeyArray(), " and PARAM_KEY=? ");
            JdbcUtils.appandSql(sqlWhere, listParams, query.getParamValue(), " and PARAM_VALUE=? ");
            JdbcUtils.appandSql(sqlWhere, listParams, query.getParamName(), " and PARAM_NAME=? ");
            JdbcUtils
                    .appandSql(sqlWhere, listParams, query.getParamValueDebug(), " and PARAM_VALUE_DEBUG=? ");
            JdbcUtils.appandSql(sqlWhere, listParams, query.getIsReadonly(), " and IS_READONLY=? ");
        }

        if (sqlWhere.length() > 0) {
            sqlWhere.delete(0, 4);
            sql.append(" where " + sqlWhere);
        }

        if (query != null && StringUtils.isNotBlank(query.getSort())) {
            sql.append(" order by " + query.getSort());
        }

        return jdbcTemplate.query(sql.toString(), listParams.toArray(), new PubParameterRowMap());
    }

    private class PubParameterResultSet implements ResultSetExtractor<SysParameter> {
        @Override
        public SysParameter extractData(ResultSet rs) throws SQLException, DataAccessException {
            if (rs.next()) {
                SysParameter o = new SysParameter();

                o.setParamKey(rs.getString("PARAM_KEY"));
                o.setParamValue(rs.getString("PARAM_VALUE"));
                o.setParamName(rs.getString("PARAM_NAME"));
                o.setParamValueDebug(rs.getString("PARAM_VALUE_DEBUG"));
                o.setReadOnly("1".equals(rs.getString("IS_READONLY")));

                return o;
            } else {
                return null;
            }
        }
    }

    private class PubParameterRowMap implements RowMapper<SysParameter> {
        @Override
        public SysParameter mapRow(ResultSet rs, int rowNum) throws SQLException {
            SysParameter o = new SysParameter();

            o.setParamKey(rs.getString("PARAM_KEY"));
            o.setParamValue(rs.getString("PARAM_VALUE"));
            o.setParamName(rs.getString("PARAM_NAME"));
            o.setParamValueDebug(rs.getString("PARAM_VALUE_DEBUG"));
            o.setReadOnly("1".equals(rs.getString("IS_READONLY")));

            return o;
        }
    }

}