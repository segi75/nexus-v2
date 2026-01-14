package com.nexus.backend.common.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

@Getter
@ToString
public class PageResponse<T> {

    private final List<T> list;      // 실제 데이터 리스트
    private final Pagination pagination; // 페이징 정보 객체

    // 생성자
    public PageResponse(List<T> list, long totalCount, SearchDto searchDto) {
        this.list = list == null ? Collections.emptyList() : list;
        this.pagination = new Pagination(totalCount, searchDto);
    }

    // 내부 클래스: 페이징 계산 로직 캡슐화
    @Getter
    public static class Pagination {
        private final long totalCount; // 전체 데이터 수
        private final int totalPage;   // 전체 페이지 수
        private final int page;        // 현재 페이지
        private final int size;        // 페이지당 개수
        private final boolean hasNext; // 다음 페이지 존재 여부

        public Pagination(long totalCount, SearchDto searchDto) {
            this.totalCount = totalCount;
            this.page = searchDto.getPage();
            this.size = searchDto.getSize();
            
            // 전체 페이지 계산
            this.totalPage = (int) Math.ceil((double) totalCount / size);
            
            // 다음 페이지 여부
            this.hasNext = page < totalPage;
        }
    }
}