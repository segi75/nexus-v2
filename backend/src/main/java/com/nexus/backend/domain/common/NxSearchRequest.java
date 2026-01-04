package com.nexus.backend.domain.common;

import lombok.Data;

@Data
public class NxSearchRequest {
    private int page = 1;      // 현재 페이지 (기본 1)
    private int size = 10;     // 페이지당 개수 (기본 10)
    
 // 추가된 검색 필드
    private String searchType; // TITLE, CONTENT, ALL
    private String keyword;    // 검색어

    // SQL에서 건너뛸 개수 계산 (MyBatis용)
    public int getOffset() {
        return (page - 1) * size;
    }
}