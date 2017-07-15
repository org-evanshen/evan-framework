package org.evanframework.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * Created by evan.shen on 2017/4/25.
 */
@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
//@MapperScan(basePackages = "org.evanframework.datadict.database.mapper")
//@ComponentScan(basePackages = "org.evanframework.datadict")
public class SpringBeansConfig implements TransactionManagementConfigurer {
    // --------------db-------------
    @Autowired
    //@Qualifier("dataSource")
    private DataSource dataSource;

    // 事务
    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
