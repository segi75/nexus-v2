package com.nexus.backend.domain.token;

import lombok.Data;

@Data
public class NxDesignToken {
    private String tokenId;
    private String tokenName;
    private String tokenValue; // JSON 문자열
    private String useYn;
}