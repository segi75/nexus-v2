package com.nexus.backend.common.exception;

import com.nexus.backend.common.dto.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice // 모든 Controller에서 발생하는 예외를 여기서 감지함
public class GlobalExceptionHandler {

    /**
     * 모든 예외(Exception)의 최종 처리
     */
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception e) {
        log.error(">>> [Global Exception] : {}", e.getMessage(), e);
        
        // 우리가 만든 표준 포맷인 status="ERROR"로 변환하여 리턴
        return ApiResponse.error("시스템 오류가 발생했습니다: " + e.getMessage());
    }

    /**
     * 필요하다면 특정 예외(예: NullPointerException)만 따로 처리 가능
     */
    /*
    @ExceptionHandler(NullPointerException.class)
    public ApiResponse<Void> handleNullPointer(NullPointerException e) {
        return ApiResponse.error("데이터가 존재하지 않습니다.");
    }
    */
}