package com.nexus.backend.common.exception;

import com.nexus.backend.common.response.NxResponse;
import com.nexus.backend.common.response.NxResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @Valid 유효성 검사 실패 시 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public NxResponse<Void> handleValidationException(MethodArgumentNotValidException e) {
        // 첫 번째 에러 메시지만 추출하여 반환
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.warn("Validation Error: {}", errorMessage);
        return NxResponse.fail(NxResultCode.INVALID_PARAMETER, errorMessage);
    }

    /**
     * 리소스를 찾을 수 없을 때 (404)
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public NxResponse<Void> handleNoResourceFoundException(NoResourceFoundException e) {
        log.warn("No Resource Found: {}", e.getMessage());
        return NxResponse.fail(NxResultCode.NOT_FOUND);
    }

    /**
     * 그 외 모든 예외 (최후의 보루)
     */
    @ExceptionHandler(Exception.class)
    public NxResponse<Void> handleException(Exception e) {
        log.error("Internal Server Error: ", e); // 스택트레이스 로깅
        return NxResponse.fail(NxResultCode.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}