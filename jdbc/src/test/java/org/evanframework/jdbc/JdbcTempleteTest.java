
package org.evanframework.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBeansConfig.class)
public class JdbcTempleteTest  {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testInsert() {
        final String sql = "insert into demo(FIELD_TEXT)values(?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"ID"});
                ps.setString(1, "我的");
                return ps;
            }
        }, keyHolder);

        int id = keyHolder.getKey().intValue();

        //		/hibernateTemplate.load(entityClass, id)

        System.out.println("pk is [" + id + "]");

    }
}
