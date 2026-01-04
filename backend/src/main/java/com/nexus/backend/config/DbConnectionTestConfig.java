package com.nexus.backend.config;

import javax.sql.DataSource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.sql.Connection;

@Configuration
public class DbConnectionTestConfig {

    @Bean
    public CommandLineRunner testConnection(
            @Qualifier("coreDataSource") DataSource coreDs,
            @Qualifier("serviceDataSource") DataSource serviceDs) {
        return args -> {
            try (Connection conn = coreDs.getConnection()) {
                System.out.println("✅ [CORE DB] 연결 성공: " + conn.getMetaData().getURL());
            } catch (Exception e) {
                System.err.println("❌ [CORE DB] 연결 실패: " + e.getMessage());
            }

            try (Connection conn = serviceDs.getConnection()) {
                System.out.println("✅ [SERVICE DB] 연결 성공: " + conn.getMetaData().getURL());
            } catch (Exception e) {
                System.err.println("❌ [SERVICE DB] 연결 실패: " + e.getMessage());
            }
        };
    }
}