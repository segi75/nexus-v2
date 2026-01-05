package com.nexus.backend.common.response;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@Builder
public class NxResponse<T> {

    private final String code;      // 비즈니스 로직 코드 (예: "200", "4001")
    private final String message;   // 응답 메시지
    private final T data;           // 실제 데이터 (Payload)
    private final LocalDateTime timestamp; // 응답 시간

    // --- 생성자 메서드 (Static Factory Methods) ---

    /**
     * 성공 응답 (데이터 있음)
     */
    public static <T> NxResponse<T> success(T data) {
        return NxResponse.<T>builder()
                .code(NxResultCode.SUCCESS.getCode())
                .message(NxResultCode.SUCCESS.getMessage())
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * 성공 응답 (데이터 없음)
     */
    public static <T> NxResponse<T> success() {
        return success(null);
    }

    /**
     * 실패/에러 응답 (커스텀 메시지)
     */
    public static <T> NxResponse<T> fail(NxResultCode resultCode, String customMessage) {
        return NxResponse.<T>builder()
                .code(resultCode.getCode())
                .message(customMessage != null ? customMessage : resultCode.getMessage())
                .data(null)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * 실패/에러 응답 (기본 메시지)
     */
    public static <T> NxResponse<T> fail(NxResultCode resultCode) {
        return fail(resultCode, null);
    }
}