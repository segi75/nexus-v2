package com.nexus.backend.common.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nexus.backend.common.api.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * [BizException] 비즈니스 로직 수행 중 발생한 의도된 예외
     * - 예: 중복 회원, 잔액 부족 등
     */
	@ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BizException.class)
    public ApiResponse<Void> handleBizException(BizException e) {
        log.warn("[BizException] : {}", e.getMessage());
        
        // [수정] ResultCode 파라미터 제거 -> 메시지만 전달
        return ApiResponse.fail(e.getMessage());
    }

    /**
     * [Valid] @Valid 유효성 검증 실패
     * - 예: 이메일 형식이 아님, 필수값 누락
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<List<String>> handleValidationException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        
        // 에러 메시지만 리스트로 추출
        List<String> errorMessages = bindingResult.getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        
        log.warn("[ValidationException] : {}", errorMessages);
        return ApiResponse.fail(errorMessages, "입력값이 올바르지 않습니다.");
    }

    /**
     * [Exception] 그 외 모든 시스템 예외 (NullPointer, DB Error 등)
     * - 보안을 위해 상세 스택트레이스는 로그에만 남기고 클라이언트엔 뭉뚱그려 전달
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleException(Exception e) {
        log.error("[System Error]", e); // 에러 로그 필수
        return ApiResponse.error("일시적인 시스템 오류가 발생했습니다. 관리자에게 문의하세요.");
    }
    
    /**
     * [405] 지원하지 않는 HTTP Method 호출 (예: POST인데 GET으로 호출)
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ApiResponse<Void> handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        log.warn("[MethodNotSupported] : {}", e.getMessage());
        return ApiResponse.fail("지원하지 않는 요청 방식입니다. (" + e.getMethod() + "은(는) 사용할 수 없습니다.)");
    }
}