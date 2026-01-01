package com.nexus.backend.domain.hello;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface HelloMapper {
    // TB_HELLO의 모든 데이터를 조회
    List<HelloVO> selectHelloList();
}