package com.nexus.backend.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NxResponse<T> {
    private String code;    // "SUCCESS" 또는 "ERROR"
    private String message; // "요청이 성공했습니다."
    private T data;         // 실제 데이터 (없으면 null)

    // 성공 시 간편하게 쓸 도우미 메서드
    public static <T> NxResponse<T> success(T data) {
        return new NxResponse<>("SUCCESS", "OK", data);
    }
    
    public static NxResponse<Void> success() {
        return new NxResponse<>("SUCCESS", "OK", null);
    }
}