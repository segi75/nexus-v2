package com.nexus.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexus.backend.domain.token.NxDesignToken;
import com.nexus.backend.repository.core.TokenMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/tokens")
@RequiredArgsConstructor
public class TokenController {

    private final TokenMapper tokenMapper;

    @GetMapping("/{tokenId}")
    public NxDesignToken getToken(@PathVariable("tokenId") String tokenId) { // 이름을 명시적으로 지정
        log.debug("NEXUS [CORE] - Fetching Design Token: {}", tokenId);
        return tokenMapper.getDesignToken(tokenId);
    }
}