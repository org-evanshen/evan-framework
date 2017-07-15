package org.evanframework.persistence.jdbc;

import org.evanframework.query.QueryParam;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * JdbcUtils
 * <p/>
 *
 * @author <a href="mailto:277469513@qq.com">Evan.Shen</a>
 * @version 2013-5-4 上午1:29:09
 */

public class JdbcUtils extends org.springframework.jdbc.support.JdbcUtils {

    private static final Logger logger = LoggerFactory.getLogger(DynamicColumnRowMap.class);

    /**
     * 为sql的where子句附件条件
     * <p/>
     * 如果value不为空，则sql.append(appendSql)，listParams.add(value)
     * <p/>
     * author: <a href="mailto:277469513@qq.com">Evan.Shen</a><br>
     * version: 2011-6-21 上午12:10:33 <br>
     *
     * @param sql        原sql
     * @param listParams 原参数列表
     * @param value      参数值
     * @param appendSql  要添加在后面的sql
     */
    public static void appandSql(StringBuilder sql, List<Serializable> listParams, Serializable value, String appendSql) {
        boolean has = false;

        if (value != null) {
            if (String.class.isInstance(value)) {
                if (StringUtils.isNotBlank(value.toString())) {
                    has = true;
                }
            } else {
                has = true;
            }
        }

        if (has) {
            sql.append(appendSql);
            listParams.add(value);
        }
    }

    /**
     * 为like语句附加sql
     * <p/>
     * <li>如果value不为空,sql.append(appendSql)</li> <li>
     * 如果likeType为LikeType.LEFT,则listParams.append("%"+value)</li> <li>
     * 如果likeType为LikeType.RIGHT,则listParams.append(value+"%")</li> <li>
     * 如果likeType为LikeType.FULL,则listParams.append("%"+value+"%")</li>
     * <p/>
     * <p/>
     * author: <a href="mailto:277469513@qq.com">Evan.Shen</a><br>
     * version: 2012-12-20 下午4:14:39 <br>
     *
     * @param sql
     * @param listParams
     * @param value
     * @param appendSql
     * @param likeType
     */
    public static void appandSqlForLike(StringBuilder sql, List<Serializable> listParams, Object value,
                                        String appendSql, LikeType likeType) {
        if (value != null && StringUtils.isNotBlank(value.toString())) {
            sql.append(appendSql);

            StringBuilder param = new StringBuilder(64);
            if (LikeType.FULL.equals(likeType) || LikeType.LEFT.equals(likeType)) {
                param.append("%");
            }

            param.append(value);

            if (LikeType.FULL.equals(likeType) || LikeType.RIGHT.equals(likeType)) {
                param.append("%");
            }
            listParams.add(param);
        }
    }

    /**
     * <p/>
     * author: <a href="mailto:277469513@qq.com">Evan.Shen</a><br>
     * version: 2011-6-21 上午09:23:12 <br>
     *
     * @param date
     */
    public static java.sql.Date convertToJavaSqlDate(java.util.Date date) {
        if (date != null) {
            return new java.sql.Date(date.getTime());
        } else {
            return null;
        }
    }

    /**
     * 格式化参数
     * <ul>
     * <li>1、将","转成"''"</li>
     * </ul>
     * author: <a href="mailto:277469513@qq.com">Evan.Shen</a><br>
     * version: 2012-2-16 下午10:51:28 <br>
     * <p/>
     *
     * @param params
     */
    public static String formatParams(Object params) {
        String returnV = null;
        if (params == null) {
            returnV = "";
        } else {
            returnV = params.toString().replace("'", "''");
        }
        return returnV;
    }

    /**
     * 为sql的where子句附件条件
     * <p/>
     * 如果value不为空，则sql.append(appendSql)，并把sql中的?替换成value的值
     * <p/>
     * <p/>
     * author: <a href="mailto:277469513@qq.com">Evan.Shen</a><br>
     * version: 2012-12-20 下午4:22:13
     * <p/>
     *
     * @param sql
     * @param value
     * @param appendSql
     */
    public static void appandSql(StringBuilder sql, Object value, String appendSql) {
        if (value != null) {
            sql.append(appendSql.replace("?", value.toString()));
        }
    }

    /**
     * 为inser语句附加sql
     * <p/>
     * 如果value不为空，则 <br>
     * sqlInsert.append(appendSqlInsert); <br>
     * sqlValues.append(appendSqlValues); <br>
     * values.add(value);
     * <p/>
     * author: <a href="mailto:277469513@qq.com">Evan.Shen</a><br>
     * version: 2012-12-20 下午4:22:37 <br>
     *
     * @param value
     * @param values
     * @param sqlInsert
     * @param appendSqlInsert
     * @param sqlValues
     * @param appendSqlValues
     */
    public static void appandSqlForInsert(Serializable value, List<Serializable> values, StringBuilder sqlInsert,
                                          String appendSqlInsert, StringBuilder sqlValues, String appendSqlValues) {
        if (value != null) {
            sqlInsert.append(appendSqlInsert);
            sqlValues.append(appendSqlValues);
            values.add(value);
        }
    }

    /**
     * 将List的值一次性设置到PreparedStatement中
     * <p/>
     * author: <a href="mailto:277469513@qq.com">Evan.Shen</a><br>
     * version: 2013-2-27 下午3:14:30 <br>
     *
     * @param ps
     * @param values
     * @throws SQLException
     */
    public static void setPreparedStatementFromValues(PreparedStatement ps, List<Serializable> values)
            throws SQLException {
        int i = 1;
        for (Serializable v : values) {
            if (Date.class.isInstance(v)) {
                Date d = (Date) v;
                ps.setTimestamp(i, new Timestamp(d.getTime()));
            } else {
                ps.setObject(i, v);
            }

            i++;
        }
    }

    public static Integer getResultSetInteger(ResultSet rs, String columnLabel) throws SQLException {
        Integer returnV = null;
        Object o = rs.getObject(columnLabel);
        if (o != null) {
            returnV = Integer.valueOf(o.toString());
        }
        return returnV;
    }

    public static Long getResultSetLong(ResultSet rs, String columnLabel) throws SQLException {
        Long returnV = null;
        Object o = rs.getObject(columnLabel);
        if (o != null) {
            returnV = Long.valueOf(o.toString());
        }
        return returnV;
    }

    public static BigDecimal getResultSetBigDecimal(ResultSet rs, String columnLabel) throws SQLException {
        BigDecimal returnV = null;
        Object o = rs.getObject(columnLabel);
        if (o != null) {
            returnV = new BigDecimal(o.toString());
        }
        return returnV;
    }

    public static Double getResultSetDouble(ResultSet rs, String columnLabel) throws SQLException {
        Double returnV = null;
        Object o = rs.getObject(columnLabel);
        if (o != null) {
            returnV = Double.valueOf(o.toString());
        }
        return returnV;
    }

    public static String getSqlColumns(String dynamicColumn, String defaultColumns) {
        return StringUtils.isNotBlank(dynamicColumn) ? dynamicColumn : defaultColumns;
    }

    /**
     * 根据指定的列构建SELECT语句 <br>
     * 如columns=["ID","USER_NAME"],则sql="select ID,USER FROM ..."
     * 如columns=null或空,则sql="select "+defaultColumns+" ..."
     * <p/>
     * author: <a href="mailto:277469513@qq.com">Evan.Shen</a><br>
     * version: 2013-2-27 上午10:49:45 <br>
     *
     * @param dynamicColumn
     */
    public static String getSqlColumns(String[] dynamicColumn, String defaultColumns) {
        StringBuilder sqlColumns = new StringBuilder(1024);
        if (dynamicColumn == null || dynamicColumn.length == 0) {
            sqlColumns.append(defaultColumns);
        } else {
            int i = 0;
            for (String column : dynamicColumn) {
                if (i > 0) {
                    sqlColumns.append(",");
                }
                sqlColumns.append(column);
                i++;
            }
        }
        return sqlColumns.toString();
    }

    @SuppressWarnings("unchecked")
    public static <T, Q> JdbcSqlQueryWrapper<T> createSqlQueryWrapper(Class<T> c, String defaultColumns,
                                                                      QueryParam query, String defaultSort,
                                                                      @SuppressWarnings("rawtypes") JdbcSqlFromAndWhereCreator jdbcSqlFromAndWhereCreator, RowMapper<T> rowMapper) {

        String sqlColumns = getSqlColumns(query.getColumns(), defaultColumns);

        JdbcSqlFromAndWhereWrapper jdbcSqlFromAndWhereWrapper = new JdbcSqlFromAndWhereWrapper();
        jdbcSqlFromAndWhereCreator.create(query, jdbcSqlFromAndWhereWrapper);

        String sqlOrderBy = getSort(query, defaultSort);

        String sqlData = " select  " + sqlColumns + " " + jdbcSqlFromAndWhereWrapper.getSqlWhere() + sqlOrderBy;

        rowMapper = getRowMapperWithColumns(c, rowMapper, query.getColumns());

        JdbcSqlQueryWrapper<T> jdbcSqlQueryWrapper = new JdbcSqlQueryWrapper<T>();

        jdbcSqlQueryWrapper.setSqlData(sqlData);
        jdbcSqlQueryWrapper.setParams(jdbcSqlFromAndWhereWrapper.getParams().toArray());

        if (query.getPageSize() > 0) {
            String sqlCount = " select count(1) as c " + jdbcSqlFromAndWhereWrapper.getSqlWhere();
            jdbcSqlQueryWrapper.setSqlCount(sqlCount);
        }

        jdbcSqlQueryWrapper.setRowMapper(rowMapper);

        return jdbcSqlQueryWrapper;
    }

    /**
     * <p>
     * <a href="mailto:277469513@qq.com">Evan.Shen</a> create at 2013-9-30
     * 上午12:44:24
     * </p>
     *
     * @param query
     * @param defaultSort
     */
    private static String getSort(QueryParam query, String defaultSort) {
        String sqlOrderBy = null;
        if (StringUtils.isNotBlank(query.getSort())) {
            sqlOrderBy = " order by " + query.getSort();
        } else if (StringUtils.isNotBlank(defaultSort)) {
            sqlOrderBy = " order by " + defaultSort;
        } else {
            sqlOrderBy = StringUtils.EMPTY;
        }
        return sqlOrderBy;
    }

    /**
     * <p/>
     * author: <a href="mailto:277469513@qq.com">Evan.Shen</a><br>
     * version: 2013-2-27 下午12:48:28 <br>
     *
     * @param c
     * @param defaultRowMapper
     * @param dynamicColumn
     */
    public static <T> RowMapper<T> getRowMapperWithColumns(Class<T> c, RowMapper<T> defaultRowMapper,
                                                           String[] dynamicColumn) {
        RowMapper<T> rm = null;
        if (dynamicColumn == null || dynamicColumn.length == 0) {
            rm = defaultRowMapper;
        } else {
            rm = new DynamicColumnRowMap<T>(c);
        }
        return rm;
    }

    public static <T> void resultSetToBean(ResultSet rs, T bean) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            Object columnValue = JdbcUtils.getResultSetValue(rs, i);
            if (columnValue != null) {
                String columnName = JdbcUtils.lookupColumnName(rsmd, i);
                String propertyName = StringUtils.uncapitalize(JdbcUtils.convertDBNameToJavaName(columnName));
                try {
                    BeanUtils.setProperty(bean, propertyName, columnValue);
                } catch (IllegalAccessException e) {
                    if (logger.isWarnEnabled()) {
                        logger.warn("属性[" + propertyName + "]赋值[" + columnValue + "]出错,请确定是否有对应的set方法，并且方法是public", e);
                    }
                } catch (InvocationTargetException e) {
                    if (logger.isWarnEnabled()) {
                        logger.warn("属性[" + propertyName + "]赋值[" + columnValue + "]出错", e);
                    }
                }
            }
        }
    }

    /**
     * 将数据库的字段名转换成javaBean的属性名
     * <p/>
     * author: <a href="mailto:277469513@qq.com">Evan.Shen</a><br>
     * version: 2013-2-27 下午3:19:21 <br>
     *
     * @param dbName
     */
    public static String convertDBNameToJavaName(String dbName) {
        StringBuilder str = new StringBuilder();
        String[] tmps = dbName.toLowerCase().split("_");

        for (String tmp : tmps) {
            str.append(StringUtils.capitalize(tmp));
        }
        return str.toString();
    }

    // public static <T> T getBeanFromResultSet(Class<T> c, ResultSet rs,
    // String[] columns) throws SQLException {
    // T o = null;
    // try {
    // o = (T) c.newInstance();
    // } catch (InstantiationException e) {
    // throw new RuntimeException(e);
    // } catch (IllegalAccessException e) {
    // throw new RuntimeException(e);
    // }
    // if (o != null) {
    // for (String column : columns) {
    // Object obj = rs.getObject(column);
    // if (obj != null) {
    // String a = JdbcUtils.toFirstCharLow(convertDBNameToJavaName(column));
    // BeanUtils.setProperty(o, a, obj);
    // }
    // }
    // }
    // return o;
    // }

    public static String mergeIntsToString(Collection<?> collection) {
        StringBuilder str = new StringBuilder(128);
        int i = 0;
        for (Object s : collection) {
            if (i > 0) {
                str.append(",");
            }
            str.append(s);
            i++;
        }
        return str.toString();
    }

    public static String mergeIntsToString(Object[] collection) {
        StringBuilder str = new StringBuilder(128);
        int i = 0;
        for (Object s : collection) {
            if (i > 0) {
                str.append(",");
            }
            str.append(s);
            i++;
        }
        return str.toString();
    }

    public static String mergeStrings(String[] args) {
        StringBuilder str = new StringBuilder(128);
        int i = 0;
        for (Object s : args) {
            if (i > 0) {
                str.append(",");
            }
            str.append("'" + s + "'");
            i++;
        }
        return str.toString();
    }

    /**
     * Like 类型
     * <p/>
     *
     * @author <a href="mailto:277469513@qq.com">Evan.Shen</a>
     * @version 2012-12-20 下午4:12:43
     */
    public enum LikeType {
        LEFT, RIGHT, FULL
    }

    // public void setPreparedStatementForInteger(PreparedStatement ps, Integer
    // value) throws SQLException {
    // if (value != null) {
    // ps.setInt(index, value);
    // index++;
    // }
    // }
    //
    // public void setPreparedStatementForString(PreparedStatement ps, String
    // value) throws SQLException {
    // if (value != null) {
    // ps.setString(index, value);
    // index++;
    // }
    // }
    //
    // public void setPreparedStatementForDouble(PreparedStatement ps, Double
    // value) throws SQLException {
    // if (value != null) {
    // ps.setDouble(index, value);
    // index++;
    // }
    // }
    //
    // public void setPreparedStatementForDate(PreparedStatement ps, Date value)
    // throws SQLException {
    // if (value != null) {
    // ps.setDate(index, JdbcUtils.convertToJavaSqlDate(value));
    // index++;
    // }
    // }
}
