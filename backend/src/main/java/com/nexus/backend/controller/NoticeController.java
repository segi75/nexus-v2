package com.nexus.backend.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.backend.domain.common.NxPageResult;
import com.nexus.backend.domain.common.NxResponse;
import com.nexus.backend.domain.common.NxSearchRequest;
import com.nexus.backend.domain.notice.NxNotice;
import com.nexus.backend.service.NoticeService; // ★ Mapper 대신 Service 임포트

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService; // ★ 의존성 변경 (Mapper -> Service)

    // 1. 리스트 조회
    @GetMapping
    public NxResponse<NxPageResult<NxNotice>> getNotices(NxSearchRequest request) {
        // 비즈니스 로직(PageResult 생성 등)은 서비스로 위임
        return NxResponse.success(noticeService.getNoticeList(request));
    }

    // 2. 상세 조회
    @GetMapping("/{noticeId}")
    public NxResponse<NxNotice> getNoticeDetail(@PathVariable("noticeId") Long noticeId) {
        return NxResponse.success(noticeService.getNoticeDetail(noticeId));
    }
    
    // 3. 게시글 등록
    @PostMapping
    public NxResponse<Void> createNotice(@RequestBody @Valid NxNotice notice) {
        log.debug("NEXUS [SERVICE] - Creating Notice: {}", notice.getTitle());
        noticeService.createNotice(notice); // 서비스 호출
        return NxResponse.success();
    }
    
    // 4. 게시글 수정
    @PutMapping("/{noticeId}")
    public NxResponse<Void> updateNotice(@PathVariable("noticeId") Long noticeId, @RequestBody @Valid NxNotice notice) {
        notice.setNoticeId(noticeId);
        noticeService.updateNotice(notice);
        return NxResponse.success();
    }

    // 5. 게시글 삭제
    @DeleteMapping("/{noticeId}")
    public NxResponse<Void> deleteNotice(@PathVariable("noticeId") Long noticeId) {
        noticeService.deleteNotice(noticeId);
        return NxResponse.success();
    }
}