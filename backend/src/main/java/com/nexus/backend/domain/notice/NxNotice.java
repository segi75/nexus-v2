package com.nexus.backend.domain.notice;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * [Domain] 공지사항 데이터 객체 (VO/DTO)
 * - Legacy 상속(NxBaseDto) 제거 -> 독립 객체화
 */
@Data
@Builder // 빌더 패턴 추가 (객체 생성 편의성)
@NoArgsConstructor
@AllArgsConstructor
public class NxNotice { 

    private Long noticeId;

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = 200, message = "제목은 200자를 넘을 수 없습니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    private int viewCount;
    
    // --- [Audit Fields] ---
    // 기존 NxBaseDto에 있었을 법한 공통 필드를 명시적으로 추가
    // (필요 없다면 제거하셔도 됩니다)
    
    private LocalDateTime regDt; // 등록일시
    private String regId;        // 등록자ID
    private LocalDateTime modDt; // 수정일시
    private String modId;        // 수정자ID
}