package com.ancun.core.persistence.jdbc;

/**
 * 查询 Sql Wrapper
 * <p/>
 *
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2013-5-4 上午1:27:50
 */
public class JdbcQuerySqlWrapper {
    private String sqlData;
    private Object[] params;

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
}
