package org.evanframework.datadict.manager;

import net.sf.ehcache.CacheManager;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.session.SqlSessionFactory;
import org.evanframework.cache.RedisTemplateCreator;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by evan.shen on 2017/4/25.
 */
@Configuration
@EnableAutoConfiguration
@EnableTransactionManagement
@MapperScan(basePackages = "org.evanframework.datadict.manager.mapper")
@ComponentScan(basePackages = "org.evanframework.datadict")
public class SpringBeansConfig implements TransactionManagementConfigurer {

    // --------------cache-------------
    @Bean(destroyMethod = "shutdown")
    public CacheManager getCacheManager() {
        CacheManager bean = new CacheManager();
        return bean;
    }

    @Bean(name = "redisTemplateCreator", destroyMethod = "destroy")
    public RedisTemplateCreator redisTemplateCreator(
            JedisConnectionFactory redisConnectionFactory) {
        RedisTemplateCreator bean = new RedisTemplateCreator(redisConnectionFactory);
        return bean;
    }

    // --------------db-------------
    @Autowired
    //@Qualifier("dataSource")
    private DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setTypeAliasesPackage("org.evanframework.datadict.manager.model");
        bean.setDataSource(dataSource);

        VendorDatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties properties = new Properties();
        properties.put("Oracle", "oracle");
        properties.put("MySQL", "mysql");
        databaseIdProvider.setProperties(properties);
        bean.setDatabaseIdProvider(databaseIdProvider);

//        // 分页插件
//        PageHelper pageHelper = new PageHelper();
//        Properties props = new Properties();
//        props.setProperty("reasonable", "true");
//        props.setProperty("supportMethodsArguments", "true");
//        props.setProperty("returnPageInfo", "check");
//        pageHelper.setProperties(props);
//        bean.setPlugins(new Interceptor[]{pageHelper});

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources("classpath*:mappers*/*Mapper.xml");
            bean.setMapperLocations(resources);
//			bean.setConfigLocation(resolver
//					.getResource("classpath:mybatis-config.xml"));
            return bean.getObject();
        } catch (Exception e) {
            throw new IllegalStateException("SqlSessionFactory init fail," + e.getMessage(), e);
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(
            SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    // 事务
    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
