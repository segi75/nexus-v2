package com.nexus.backend.common.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchDto {

    private int page = 1;      // 현재 페이지 (기본값 1)
    private int size = 10;     // 페이지당 개수 (기본값 10)
    private String keyword;    // 검색어
    private String searchType; // 검색 조건 (제목, 내용 등)

    /**
     * Mybatis/JPA 등에서 사용할 Offset 계산 (0부터 시작)
     */
    public int getOffset() {
        return (Math.max(1, page) - 1) * Math.max(1, size);
    }
}