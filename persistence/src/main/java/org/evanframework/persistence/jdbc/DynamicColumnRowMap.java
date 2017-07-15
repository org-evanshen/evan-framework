package org.evanframework.persistence.jdbc;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 动态列的RowMap
 * <p/>
 *
 * @param <T>
 * @author <a href="mailto:277469513@qq.com">Evan.Shen</a>
 * @version 2013-2-27 下午3:22:11
 */
public class DynamicColumnRowMap<T> implements RowMapper<T> {

    private Class<T> c;

    public DynamicColumnRowMap(Class<T> c) {
        super();
        this.c = c;
    }

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        T bean = null;
        try {
            bean = c.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        if (bean != null) {
            JdbcUtils.resultSetToBean(rs, bean);
        }

        return bean;
    }


}