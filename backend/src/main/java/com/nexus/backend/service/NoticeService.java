package com.nexus.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexus.backend.common.dto.PageResponse;  // [New] 표준 응답 객체
import com.nexus.backend.common.dto.SearchDto;     // [New] 표준 검색 요청 객체
import com.nexus.backend.domain.notice.NxNotice;
import com.nexus.backend.domain.notice.mapper.NoticeMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeMapper noticeMapper;

    /**
     * 공지사항 리스트 조회 (표준화 적용)
     */
    public PageResponse<NxNotice> getNoticeList(SearchDto request) {
        // 1. 데이터 조회
        List<NxNotice> list = noticeMapper.selectNoticeList(request);
        
        // 2. 전체 카운트 조회
        long total = noticeMapper.countNoticeList(request);
        
        // 3. 표준 PageResponse 생성 (List, TotalCount, SearchDto 전달)
        // 기존 new NxPageResult<>(...) -> new PageResponse<>(...) 로 변경
        return new PageResponse<>(list, total, request);
    }

    /**
     * 공지사항 상세 조회
     */
    public NxNotice getNoticeDetail(Long noticeId) {
        // 만약 데이터가 없으면 예외를 던지는 로직을 추가하는 것이 3.0 표준입니다.
        // 현재는 null 리턴 가능성을 열어두되, 추후 orElseThrow() 패턴 적용 권장
        return noticeMapper.getNoticeDetail(noticeId);
    }

    /**
     * 공지사항 등록
     */
    @Transactional // 쓰기 작업 (readOnly = false)
    public void createNotice(NxNotice notice) {
        noticeMapper.insertNotice(notice);
    }

    /**
     * 공지사항 수정
     */
    @Transactional
    public void updateNotice(NxNotice notice) {
        noticeMapper.updateNotice(notice);
    }

    /**
     * 공지사항 삭제
     */
    @Transactional
    public void deleteNotice(Long noticeId) {
        noticeMapper.deleteNotice(noticeId);
    }
}