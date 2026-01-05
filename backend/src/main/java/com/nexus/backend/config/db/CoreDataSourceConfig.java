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

import com.nexus.backend.global.annotation.CoreMapper; // ★ 명찰 임포트

@Configuration
@MapperScan(
    basePackages = "com.nexus.backend.domain", // 1. 도메인 패키지 전체를 스캔
    annotationClass = CoreMapper.class,        // 2. @CoreMapper 붙은 녀석만 내 식구다!
    sqlSessionFactoryRef = "coreSqlSessionFactory"
)
public class CoreDataSourceConfig {

	@Primary
    @Bean(name = "coreDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.core")
    public DataSource coreDataSource() {
        return DataSourceBuilder.create()
            // ▼ [핵심] 여기서 강제로 신버전 드라이버를 박아버립니다.
            .driverClassName("com.mysql.cj.jdbc.Driver") 
            .build();
    }
	
    @Primary
    @Bean(name = "coreSqlSessionFactory")
    public SqlSessionFactory coreSqlSessionFactory(@Qualifier("coreDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        
        org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
        config.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(config);
        
        // XML 위치는 유지 (DB별로 모아두는 것이 관리에 용이함)
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/core/*.xml"));
        return bean.getObject();
    }

    @Primary
    @Bean(name = "coreTransactionManager")
    public DataSourceTransactionManager coreTransactionManager(@Qualifier("coreDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}