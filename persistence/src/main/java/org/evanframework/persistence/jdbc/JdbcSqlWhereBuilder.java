package org.evanframework.persistence.jdbc;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 查询语句构建器
 * <p/>
 *
 * @author <a href="mailto:277469513@qq.com">Evan.Shen</a>
 * @version 2013-5-5 上午10:43:06
 */

public class JdbcSqlWhereBuilder {
    private StringBuilder sql = new StringBuilder(768);

    private List<Serializable> params = null;

    public JdbcSqlWhereBuilder(List<Serializable> listParams) {
        this.params = listParams;
        this.sql.append(sql);
    }

    /**
     * 如果queryValue不为空， 则sql.append(appendSql),listParams.add(queryValue)
     * <p/>
     * author: <a href="mailto:277469513@qq.com">Evan.Shen</a><br>
     * version: 2013-5-5 上午11:49:48 <br>
     *
     * @param appendSql
     * @param queryValue
     */
    public void appendByQueryValue(String appendSql, Serializable queryValue) {
        JdbcUtils.appandSql(sql, params, queryValue, appendSql);
    }

    /**
     * 如果queryValue不为空， 则sql.append(appendSql.replace("?", queryValue)))
     * <p/>
     * author: <a href="mailto:277469513@qq.com">Evan.Shen</a><br>
     * version: 2013-5-5 上午11:51:25 <br>
     *
     * @param appendSql
     * @param queryValue
     */
    public void appendAndReplaceParamsByQueryValue(String appendSql, Object queryValue) {
        JdbcUtils.appandSql(sql, queryValue, appendSql);
    }

    /**
     * --sql.append(appendSql);<br>
     * --listParams.add( date);
     * <p/>
     * author: <a href="mailto:277469513@qq.com">Evan.Shen</a><br>
     * version: 2013-5-5 上午11:55:17 <br>
     *
     * @param appendSql
     * @param date
     */
    public void appendForDateByQueryValue(String appendSql, Date date) {
        JdbcUtils.appandSql(sql, params, date, appendSql);
    }

    public void appendForLikeByQueryValue(String appendSql, Object queryValue, JdbcUtils.LikeType likeType) {
        JdbcUtils.appandSqlForLike(sql, params, queryValue, appendSql, likeType);
    }

    public void append(String appendSql) {
        sql.append(appendSql);
    }

    public String getSqlWhere() {
        return sql.toString();
    }

    public List<Serializable> getParams() {
        return params;
    }
}
