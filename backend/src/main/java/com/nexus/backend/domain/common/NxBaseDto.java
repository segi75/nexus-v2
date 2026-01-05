package com.nexus.backend.domain.common;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * [NEXUS Core] 도메인 공통 부모 클래스
 * 모든 DTO는 이 클래스를 상속받아 Audit(감사) 필드를 자동 보유함.
 */
@Getter
@Setter
public class NxBaseDto {

    // 등록 정보 (Who & When)
    private String regId;          // 등록자 ID (로그인 사용자)
    private LocalDateTime regDt;   // 등록일시

    // 수정 정보 (Who & When)
    private String modId;          // 수정자 ID (로그인 사용자)
    private LocalDateTime modDt;   // 수정일시
}