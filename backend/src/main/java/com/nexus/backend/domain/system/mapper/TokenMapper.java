package com.nexus.backend.domain.system.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.nexus.backend.domain.token.NxDesignToken;
import com.nexus.backend.global.annotation.CoreMapper;

@Mapper
@CoreMapper
public interface TokenMapper {
    NxDesignToken getDesignToken(String tokenId);
}