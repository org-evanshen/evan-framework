package org.evanframework.jdbc.jdbc;

import org.evanframework.model.query.QueryParam;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JdbcDao Oracle service
 *
 * @author <a href="mailto:277469513@qq.com">Evan.Shen</a>
 * @version Date: 2010-10-26 12:32:45
 */
public class JdbcDaoOracle extends AbstractJdbcDao implements JdbcDao {
    // private final Logger logger = LoggerFactory.getLogger(JdbcDao.class);

    public JdbcDaoOracle(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getSqlPage(QueryParam query, String sqlData) {
        int startRow, endRow;// oracle rownum
        startRow = (query.getPageNo() - 1) * query.getPageSize() + 1;
        endRow = query.getPageNo() * query.getPageSize();

        StringBuilder sqlPage = new StringBuilder();
        if (startRow > 1) {
            sqlPage.append("select * from (");
        }
        if (endRow > 1) {
            sqlPage.append("select w1.*,rownum as rn from (");
        }
        sqlPage.append(sqlData);
        if (endRow > 1) {
            sqlPage.append(") w1 where rownum <= " + endRow);
        }
        if (startRow > 1) {
            sqlPage.append(") w2 where rn >= " + startRow);
        }
        return sqlPage.toString();
    }

    @Override
    public String getSysDate() {
        return "sysdate";
    }

    @Override
    public String getToDate() {
        return "TO_DATE(?,'yyyy-mm-dd')";
    }

    @Override
    public String getStringContectSymbol() {
        return "||";
    }

}
