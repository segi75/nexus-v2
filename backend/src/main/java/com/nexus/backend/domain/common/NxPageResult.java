package com.nexus.backend.domain.common;

import java.util.List;
import lombok.Data;

@Data
public class NxPageResult<T> {
    private List<T> list;      // 실제 데이터
    private long totalCount;   // 전체 데이터 개수
    private int currentPage;   // 현재 페이지

    public NxPageResult(List<T> list, long totalCount, int currentPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.currentPage = currentPage;
    }
}