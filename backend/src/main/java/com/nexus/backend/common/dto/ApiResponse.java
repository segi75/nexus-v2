package com.nexus.backend.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {
    
    // 응답 상태 ("SUCCESS", "FAIL", "ERROR")
    private String status;
    
    // 응답 메시지 (디버깅용 또는 사용자 알림용)
    private String message;
    
    // 실제 데이터 (Generic으로 어떤 타입이든 담을 수 있게 설정)
    private T data;

    // 생성자 (외부에서 직접 new 하지 못하도록 private 또는 protected 권장)
    private ApiResponse(String status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // 성공 시 호출할 static 메서드 (데이터 있음)
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("SUCCESS", "OK", data);
    }

    // 성공 시 호출할 static 메서드 (데이터 없음 - 예: 삭제 성공)
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>("SUCCESS", "OK", null);
    }
    
    // 실패 시 호출할 static 메서드 (나중에 ExceptionHandler에서 사용)
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>("ERROR", message, null);
    }
}