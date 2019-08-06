package org.evanframework.jdbc.jdbc;

import org.evanframework.model.query.QueryParam;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcDaoMysql extends AbstractJdbcDao implements JdbcDao {

    public JdbcDaoMysql(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    @Override
    protected String getSqlPage(QueryParam query, String sqlData) {
        return null;
    }

    @Override
    public String getSysDate() {
        return "now()";
    }

    @Override
    public String getToDate() {
        return null;
    }

    @Override
    public String getStringContectSymbol() {
        return null;
    }
}