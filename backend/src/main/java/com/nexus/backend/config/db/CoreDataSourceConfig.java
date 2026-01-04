package com.nexus.backend.config.db;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@MapperScan(
    basePackages = "com.nexus.backend.repository.core",
    sqlSessionFactoryRef = "coreSqlSessionFactory"
)
public class CoreDataSourceConfig {

    @Primary
    @Bean(name = "coreDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.core")
    public DataSource coreDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "coreSqlSessionFactory")
    public SqlSessionFactory coreSqlSessionFactory(@Qualifier("coreDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        
        // MyBatis 설정: CamelCase 적용
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(config);
        
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/core/*.xml"));
        return bean.getObject();
    }

    @Primary
    @Bean(name = "coreTransactionManager")
    public DataSourceTransactionManager coreTransactionManager(@Qualifier("coreDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}