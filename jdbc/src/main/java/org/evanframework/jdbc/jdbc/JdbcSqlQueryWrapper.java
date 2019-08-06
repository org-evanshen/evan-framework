package org.evanframework.jdbc.jdbc;

import org.springframework.jdbc.core.RowMapper;

public class JdbcSqlQueryWrapper<T> {
    private String sqlData;
    private String sqlCount;
    private Object[] params;
    private RowMapper<T> rowMapper;

    public String getSqlData() {
        return sqlData;
    }

    public void setSqlData(String sqlData) {
        this.sqlData = sqlData;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    public String getSqlCount() {
        return sqlCount;
    }

    public void setSqlCount(String sqlCount) {
        this.sqlCount = sqlCount;
    }

    public RowMapper<T> getRowMapper() {
        return rowMapper;
    }

    public void setRowMapper(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
    }
}
