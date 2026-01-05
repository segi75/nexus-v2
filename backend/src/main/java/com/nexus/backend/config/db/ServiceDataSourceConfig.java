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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.nexus.backend.global.annotation.ServiceMapper; // ★ 명찰 임포트

@Configuration
@MapperScan(
    basePackages = "com.nexus.backend.domain", // 1. 도메인 패키지 전체 스캔
    annotationClass = ServiceMapper.class,     // 2. @ServiceMapper 붙은 녀석만 내 식구다!
    sqlSessionFactoryRef = "serviceSqlSessionFactory"
)
public class ServiceDataSourceConfig {

    @Bean(name = "serviceDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.service")
    public DataSource serviceDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "serviceSqlSessionFactory")
    public SqlSessionFactory serviceSqlSessionFactory(@Qualifier("serviceDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(config);
        
        // XML 위치 유지
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/service/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "serviceTransactionManager")
    public DataSourceTransactionManager serviceTransactionManager(@Qualifier("serviceDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}