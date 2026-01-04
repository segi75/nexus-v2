package com.nexus.backend.domain.notice;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NxNotice {
    private Long noticeId;
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    @Size(max = 100, message = "제목은 100자를 넘을 수 없습니다.")
    private String title;

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;
    private Integer viewCount;
    private LocalDateTime regDt;
}