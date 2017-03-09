package com.ancun.core.persistence.jdbc;

import com.ancun.core.query.QueryParam;

/**
 * from, where 子句构造器
 * <p/>
 *
 * @author <a href="mailto:shenwei@ancun.com">ShenWei</a>
 * @version 2013-5-4 上午1:28:10
 */
public interface JdbcSqlFromAndWhereCreator<T extends QueryParam> {
    void create(T query, JdbcSqlFromAndWhereWrapper builder);
}
