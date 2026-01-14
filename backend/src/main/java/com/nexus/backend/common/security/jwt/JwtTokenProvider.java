package com.nexus.backend.common.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * NEXUS Framework v3.0
 * JWT 토큰 생성 및 검증을 담당하는 Provider
 */
@Slf4j
@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_KEY = "auth"; // 권한 정보 키
    private static final String BEARER_TYPE = "Bearer";   // 토큰 타입

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long accessTokenValidityInMilliseconds;

    private SecretKey key;

    // 객체 초기화 후 Secret Key 생성
    @PostConstruct
    public void init() {
        // application.yml의 문자열 키를 HMAC-SHA 알고리즘용 객체로 변환
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 1. 토큰 생성 (Create Token)
     * 유저 정보를 받아서 Access Token을 생성합니다.
     */
    public String createToken(Authentication authentication) {
        // 권한 가져오기 (예: ROLE_USER, ROLE_ADMIN)
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + accessTokenValidityInMilliseconds);

        return Jwts.builder()
                .subject(authentication.getName())      // ID (Subject)
                .claim(AUTHORITIES_KEY, authorities)    // 권한 정보 (Claim)
                .issuedAt(new Date())                   // 발행 시간
                .expiration(validity)                   // 만료 시간
                .signWith(key)                          // 암호화 (서명)
                .compact();
    }

    /**
     * 2. 토큰에서 인증 정보 조회 (Get Authentication)
     * 토큰을 복호화하여 Spring Security가 이해할 수 있는 Authentication 객체를 만듭니다.
     */
    public Authentication getAuthentication(String token) {
        // 토큰 복호화
        Claims claims = parseClaims(token);

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // UserDetails 객체를 만들어서 Authentication 리턴
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    /**
     * 3. 토큰 유효성 검증 (Validate Token)
     * 토큰이 위변조되지 않았는지, 만료되지 않았는지 체크합니다.
     */
    public boolean validateToken(String token) {
        try {
            // jjwt 0.12.x 문법: parser().verifyWith(key).build().parseSignedClaims(token)
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    /**
     * 4. Request Header에서 토큰 추출
     * "Authorization: Bearer <token>" 형태에서 토큰만 잘라냅니다.
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE + " ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 내부 유틸: 토큰 파싱
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(accessToken)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}