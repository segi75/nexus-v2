package com.nexus.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexus.backend.domain.common.NxPageResult;
import com.nexus.backend.domain.common.NxSearchRequest;
import com.nexus.backend.domain.notice.NxNotice;
import com.nexus.backend.domain.notice.mapper.NoticeMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 기본적으로 읽기 전용 (성능 최적화)
public class NoticeService {

    private final NoticeMapper noticeMapper;

    /**
     * 공지사항 리스트 조회
     */
    public NxPageResult<NxNotice> getNoticeList(NxSearchRequest request) {
        List<NxNotice> list = noticeMapper.selectNoticeList(request);
        long total = noticeMapper.countNoticeList(request);
        return new NxPageResult<>(list, total, request.getPage());
    }

    /**
     * 공지사항 상세 조회
     */
    public NxNotice getNoticeDetail(Long noticeId) {
        // 추후 조회수 증가 로직 등이 여기에 추가될 수 있음
        return noticeMapper.getNoticeDetail(noticeId);
    }

    /**
     * 공지사항 등록
     */
    @Transactional // 쓰기 작업이므로 readOnly = false 적용
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