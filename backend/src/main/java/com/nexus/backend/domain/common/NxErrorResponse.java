package com.nexus.backend.domain.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NxErrorResponse {
    private String code;    // 예: "BAD_REQUEST"
    private String message; // 예: "제목은 필수 입력 값입니다."
}