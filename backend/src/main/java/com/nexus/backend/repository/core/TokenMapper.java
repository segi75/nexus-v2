package com.nexus.backend.repository.core;

import org.apache.ibatis.annotations.Mapper;
import com.nexus.backend.domain.token.NxDesignToken;

@Mapper
public interface TokenMapper {
    NxDesignToken getDesignToken(String tokenId);
}