package com.nexus.backend.domain.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.nexus.backend.domain.common.NxSearchRequest;
import com.nexus.backend.domain.notice.NxNotice;
import com.nexus.backend.global.annotation.ServiceMapper;

@Mapper
@ServiceMapper
public interface NoticeMapper {
	
	List<NxNotice> selectNoticeList(NxSearchRequest request); // 데이터 조회
	
	long countNoticeList(NxSearchRequest request);            // 전체 개수 조회
    
	NxNotice getNoticeDetail(Long noticeId);
	
	void insertNotice(NxNotice notice);
	void updateNotice(NxNotice notice);
	void deleteNotice(Long noticeId);
}