package com.nexus.backend.common.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 기본 생성자 차단 (JPA/Jackson용 protected는 허용)
@JsonInclude(JsonInclude.Include.NON_NULL) // data가 null이면 응답 JSON에서 제외
public class ApiResponse<T> {

    private ResultCode result;
    private String message;
    private T data;

    // private 생성자 (외부에서 new 사용 금지)
    private ApiResponse(ResultCode result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    // ==========================================
    // Factory Methods (성공)
    // ==========================================

    /**
     * [성공] 데이터가 있는 경우
     * 메시지는 ResultCode.SUCCESS의 기본 메시지 사용
     */
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ResultCode.SUCCESS, ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * [성공] 데이터가 있고, 메시지를 커스텀하고 싶은 경우
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(ResultCode.SUCCESS, message, data);
    }

    /**
     * [성공] 데이터 없이 성공 메시지만 보낼 경우 (예: 삭제 성공)
     */
    public static <T> ApiResponse<T> ok() {
        return new ApiResponse<>(ResultCode.SUCCESS, ResultCode.SUCCESS.getMessage(), null);
    }

    // ==========================================
    // Factory Methods (실패/에러)
    // ==========================================

    /**
     * [실패] 비즈니스 로직 실패 (기본 메시지 사용)
     */
    public static <T> ApiResponse<T> fail() {
        return new ApiResponse<>(ResultCode.FAIL, ResultCode.FAIL.getMessage(), null);
    }

    /**
     * [실패] 비즈니스 로직 실패 (커스텀 메시지)
     * 예: "중복된 ID입니다."
     */
    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(ResultCode.FAIL, message, null);
    }

    /**
     * [실패] 비즈니스 로직 실패 (데이터 포함)
     * 예: 실패 원인이 담긴 데이터 리턴
     */
    public static <T> ApiResponse<T> fail(T data, String message) {
        return new ApiResponse<>(ResultCode.FAIL, message, data);
    }

    /**
     * [에러] 시스템 에러 (커스텀 메시지)
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(ResultCode.ERROR, message, null);
    }
}