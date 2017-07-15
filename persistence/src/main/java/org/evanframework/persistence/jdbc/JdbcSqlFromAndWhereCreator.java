package org.evanframework.persistence.jdbc;

/**/

import org.evanframework.query.QueryParam;

/**
 * from, where 子句构造器
 * <p/>
 *
 * @author <a href="mailto:277469513@qq.com">Evan.Shen</a>
 * @version 2013-5-4 上午1:28:10
 */
public interface JdbcSqlFromAndWhereCreator<T extends QueryParam> {
    void create(T query, JdbcSqlFromAndWhereWrapper builder);
}
