package com.nexus.backend.common.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultCode {
    
    SUCCESS("정상적으로 처리되었습니다."),
    FAIL("요청 처리에 실패하였습니다."),
    ERROR("서버 에러가 발생하였습니다.");

    private final String message; // 기본 메시지
}