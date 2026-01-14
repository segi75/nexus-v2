package com.nexus.backend.controller;

import com.nexus.backend.common.api.ApiResponse;    // [New] 표준 응답
import com.nexus.backend.common.dto.PageResponse;    // [New] 표준 페이징 응답
import com.nexus.backend.common.dto.SearchDto;       // [New] 표준 검색 요청
import com.nexus.backend.domain.notice.NxNotice;
import com.nexus.backend.service.NoticeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // 1. 리스트 조회 (표준 페이징 적용)
    // Return Type: ApiResponse < PageResponse < NxNotice >>
    @GetMapping
    public ApiResponse<PageResponse<NxNotice>> getNotices(SearchDto request) {
        // Service가 PageResponse<NxNotice>를 반환하도록 수정되어야 함
        return ApiResponse.success(noticeService.getNoticeList(request));
    }

    // 2. 상세 조회
    @GetMapping("/{noticeId}")
    public ApiResponse<NxNotice> getNoticeDetail(@PathVariable("noticeId") Long noticeId) {
        return ApiResponse.success(noticeService.getNoticeDetail(noticeId));
    }
    
    // 3. 게시글 등록
    @PostMapping
    public ApiResponse<Void> createNotice(@RequestBody @Valid NxNotice notice) {
        log.debug("NEXUS [SERVICE] - Creating Notice: {}", notice.getTitle());
        noticeService.createNotice(notice);
        // 데이터 없이 성공 메시지만 반환할 때는 ok() 사용
        return ApiResponse.ok(); 
    }
    
    // 4. 게시글 수정
    @PutMapping("/{noticeId}")
    public ApiResponse<Void> updateNotice(@PathVariable("noticeId") Long noticeId, @RequestBody @Valid NxNotice notice) {
        notice.setNoticeId(noticeId); // (참고: Entity에 Setter보다는 별도 메서드 권장)
        noticeService.updateNotice(notice);
        return ApiResponse.ok();
    }

    // 5. 게시글 삭제
    @DeleteMapping("/{noticeId}")
    public ApiResponse<Void> deleteNotice(@PathVariable("noticeId") Long noticeId) {
        noticeService.deleteNotice(noticeId);
        return ApiResponse.ok();
    }
}