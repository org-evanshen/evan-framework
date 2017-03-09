package com.ancun.core.persistence.jdbc;

import com.ancun.core.query.QueryParam;

/**
 * 分页Sql Wrapper
 * <p/>
 *
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2013-5-4 上午1:27:21
 */
public class JdbcPageSqlWrapper extends JdbcQuerySqlWrapper {
    private String sqlCount;
    private QueryParam query;

    public String getSqlCount() {
        return sqlCount;
    }

    public void setSqlCount(String sqlCount) {
        this.sqlCount = sqlCount;
    }

    public QueryParam getQuery() {
        return query;
    }

    public void setQuery(QueryParam query) {
        this.query = query;
    }


}
