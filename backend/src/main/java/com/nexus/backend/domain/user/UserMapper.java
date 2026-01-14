package com.nexus.backend.domain.user;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.nexus.backend.global.annotation.CoreMapper;

@CoreMapper // MySQL DB 사용 명시
@Mapper
public interface UserMapper {
    
    // 1. 로그인용: ID로 사용자 정보 조회
    NxUser findByUserId(@Param("userId") String userId);

    // 2. 회원가입용: 사용자 정보 저장
    void save(NxUser user);
}