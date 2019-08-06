package org.evanframework.jdbc.jdbc;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

public class JdbcDaoFactoryBean implements FactoryBean<JdbcDao>, InitializingBean {
    private String jdbcDriverClassName;

    private JdbcTemplate jdbcTemplate;

    private JdbcDao jdbcDao;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(jdbcDriverClassName,"jdbcDriverClassName can not null");
        Assert.notNull(jdbcTemplate,"jdbcTemplate can not null");

        String jdbcDriverClassNameLower = jdbcDriverClassName.toLowerCase();

        if (StringUtils.contains(jdbcDriverClassNameLower, "mysql")) {
            jdbcDao = new JdbcDaoMysql(jdbcTemplate);
        } else if (StringUtils.contains(jdbcDriverClassNameLower, "oracle")) {
            jdbcDao = new JdbcDaoOracle(jdbcTemplate);
        }
    }

    public void setJdbcDriverClassName(String jdbcDriverClassName) {
        this.jdbcDriverClassName = jdbcDriverClassName;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public JdbcDao getObject() throws Exception {
        return jdbcDao;
    }

    @Override
    public Class<? extends JdbcDao> getObjectType() {
        return this.jdbcDao == null ? JdbcDao.class : this.jdbcDao.getClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
