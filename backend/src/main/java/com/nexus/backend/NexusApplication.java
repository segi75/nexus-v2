package com.nexus.backend;

import org.mybatis.spring.annotation.MapperScan; // 임포트 추가
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// [추가] 아래 줄을 추가해서 Mapper 인터페이스가 있는 경로를 강제로 지정합니다.
@MapperScan("com.nexus.backend.domain.hello") 
public class NexusApplication {
    public static void main(String[] args) {
        SpringApplication.run(NexusApplication.class, args);
    }
}