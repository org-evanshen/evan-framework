package org.evanframework.jdbc.jdbc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询语句构建器
 * <p/>
 *
 * @author <a href="mailto:277469513@qq.com">Evan.Shen</a>
 * @version 2013-5-5 上午10:43:06
 */
public class JdbcSqlFromAndWhereWrapper {
    private StringBuilder sql = new StringBuilder(768);
    private List<Serializable> params = new ArrayList<Serializable>(32);

    /**
     * 如果queryValue不为空， 则sql.append(appendSql),listParams.add(queryValue)
     * <p/>
     * author: <a href="mailto:277469513@qq.com">Evan.Shen</a><br>
     * version: 2013-5-5 上午11:49:48 <br>
     *
     * @param appendSql
     * @param queryValue
     */
    public void append(String appendSql, Serializable queryValue) {
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
    public void appendAndReplaceParams(String appendSql, Object queryValue) {
        JdbcUtils.appandSql(sql, queryValue, appendSql);
    }

    public void append(String appendSql, Object queryValue, JdbcUtils.LikeType likeType) {
        JdbcUtils.appandSqlForLike(sql, params, queryValue, appendSql, likeType);
    }

    public void append(String appendSql) {
        sql.append(appendSql);
    }

    public void insert(int index, String appendSql) {
        sql.insert(index, appendSql);
    }

    public void replace(int start, int end, String str) {
        sql.replace(start, end, str);
    }

    public boolean isEmpty() {
        return sql.length() == 0;
    }

    public boolean isNotEmpty() {
        return sql.length() > 0;
    }

    public String getSqlWhere() {
        return sql.toString();
    }

    public List<Serializable> getParams() {
        return params;
    }
}
