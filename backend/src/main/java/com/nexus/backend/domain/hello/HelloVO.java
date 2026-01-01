package com.nexus.backend.domain.hello;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * TB_HELLO 테이블과 매핑되는 Value Object
 */
@Data
public class HelloVO {
    // 어제 생성하신 TB_HELLO 컬럼명과 일치시켜 주세요.
    // 예시: id, title, content
	private Long id;            // DB: ID
    private String message;     // DB: MESSAGE (기존 content/title 삭제 후 추가)
    private LocalDateTime regDt; // DB: REG_DT (스네이크 케이스 -> 카멜 케이스 변환)
}