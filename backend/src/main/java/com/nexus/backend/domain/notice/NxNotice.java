package com.nexus.backend.domain.notice;

import com.nexus.backend.domain.common.NxBaseDto; // 부모 클래스 임포트

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false) // 부모 필드는 equals 계산에서 제외 (Lombok 경고 방지)
public class NxNotice extends NxBaseDto { // ★ [핵심] 상속 적용

    private Long noticeId;

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 200, message = "제목은 200자를 넘을 수 없습니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    private int viewCount;

}