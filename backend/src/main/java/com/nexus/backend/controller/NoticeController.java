package com.nexus.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.backend.domain.common.NxPageResult;
import com.nexus.backend.domain.common.NxResponse; // ★ 표준 응답 객체 임포트
import com.nexus.backend.domain.common.NxSearchRequest;
import com.nexus.backend.domain.notice.NxNotice;
import com.nexus.backend.repository.service.NoticeMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeMapper noticeMapper;

    // 1. 리스트 조회 (페이징)
    // 반환 타입 변경: NxPageResult -> NxResponse<NxPageResult>
    @GetMapping
    public NxResponse<NxPageResult<NxNotice>> getNotices(NxSearchRequest request) {
        log.debug("NEXUS [SERVICE] - Fetching Notice List Page: {}", request.getPage());
        
        List<NxNotice> list = noticeMapper.selectNoticeList(request);
        long total = noticeMapper.countNoticeList(request);
        
        // NxResponse.success()로 포장해서 리턴
        return NxResponse.success(new NxPageResult<>(list, total, request.getPage()));
    }

    // 2. 상세 조회
    // 반환 타입 변경: NxNotice -> NxResponse<NxNotice>
    @GetMapping("/{noticeId}")
    public NxResponse<NxNotice> getNoticeDetail(@PathVariable("noticeId") Long noticeId) {
        log.debug("NEXUS [SERVICE] - Fetching Notice Detail ID: {}", noticeId);
        return NxResponse.success(noticeMapper.getNoticeDetail(noticeId));
    }
    
    // 3. 게시글 등록
    // 반환 타입 변경: void -> NxResponse<Void>
    @PostMapping
    public NxResponse<Void> createNotice(@RequestBody @Valid NxNotice notice) {
        log.debug("NEXUS [SERVICE] - Creating Notice: {}", notice.getTitle());
        noticeMapper.insertNotice(notice);
        return NxResponse.success(); // 데이터 없이 성공 상태만 리턴
    }
    
    // 4. 게시글 수정
    @PutMapping("/{noticeId}")
    public NxResponse<Void> updateNotice(@PathVariable("noticeId") Long noticeId, @RequestBody @Valid NxNotice notice) {
        log.debug("NEXUS [SERVICE] - Updating Notice ID: {}", noticeId);
        notice.setNoticeId(noticeId);
        noticeMapper.updateNotice(notice);
        return NxResponse.success();
    }

    // 5. 게시글 삭제
    @DeleteMapping("/{noticeId}")
    public NxResponse<Void> deleteNotice(@PathVariable("noticeId") Long noticeId) {
        log.debug("NEXUS [SERVICE] - Deleting Notice ID: {}", noticeId);
        noticeMapper.deleteNotice(noticeId);
        return NxResponse.success();
    }
}