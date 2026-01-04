package com.nexus.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.nexus.backend.domain.common.NxErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice // 모든 컨트롤러에서 발생하는 예외를 여기서 잡습니다.
public class GlobalExceptionHandler {

    // 유효성 검사(@Valid) 실패 시 실행
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<NxErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // 첫 번째 에러 메시지만 가져옴 (예: "제목은 필수 입력 값입니다.")
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        
        log.error("NEXUS [VALIDATION_FAIL] - {}", errorMessage);

        // 400 Bad Request 상태코드와 함께 메시지 반환
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new NxErrorResponse("VALIDATION_ERROR", errorMessage));
    }

    // 그 외 예상치 못한 모든 에러 처리
    @ExceptionHandler(Exception.class)
    public ResponseEntity<NxErrorResponse> handleAllExceptions(Exception ex) {
        log.error("NEXUS [SYSTEM_ERROR]", ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new NxErrorResponse("SYSTEM_ERROR", "서버 내부 오류가 발생했습니다. 관리자에게 문의하세요."));
    }
}