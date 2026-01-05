package com.nexus.backend.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NxResultCode {

    // 정상 처리
    SUCCESS("200", "정상적으로 처리되었습니다."),
    
    // 클라이언트 오류 (400번대)
    BAD_REQUEST("400", "잘못된 요청입니다."),
    INVALID_PARAMETER("4001", "유효하지 않은 파라미터입니다."),
    NOT_FOUND("404", "리소스를 찾을 수 없습니다."),
    
    // 서버 오류 (500번대)
    INTERNAL_SERVER_ERROR("500", "서버 내부 오류가 발생했습니다."),
    DB_ERROR("5001", "데이터베이스 처리 중 오류가 발생했습니다.");

    private final String code;
    private final String message;
}