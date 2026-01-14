package com.nexus.backend.service;

import com.nexus.backend.common.exception.BizException; // [Standard] 예외 처리
import com.nexus.backend.common.security.jwt.JwtTokenProvider;
import com.nexus.backend.domain.user.NxLoginRequest;
import com.nexus.backend.domain.user.NxUser;
import com.nexus.backend.domain.user.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 로그인 (JWT 발급)
     */
    @Transactional(readOnly = true)
    public String login(NxLoginRequest request) {
        
        // 1. ID 존재 여부 확인
        NxUser user = userMapper.findByUserId(request.getUserId());
        if (user == null) {
            // 보안상 "아이디가 없음"과 "비번 틀림"을 구분하지 않는 것이 좋으나, 
            // 개발 편의를 위해 명시합니다. (운영 시 메시지 통일 권장)
            throw new BizException("존재하지 않는 아이디입니다.");
        }

        // 2. 비밀번호 검증 (DB 암호문 vs 입력값)
        if (!passwordEncoder.matches(request.getUserPw(), user.getUserPw())) {
            throw new BizException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 인증 객체 생성 (이미 검증되었으므로 강제로 생성)
        // NxUser가 UserDetails를 구현했으므로 바로 넣을 수 있음
        Authentication authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

        // 4. JWT 토큰 생성 및 반환
        return jwtTokenProvider.createToken(authenticationToken);
    }
    
    /**
     * 회원가입
     */
    @Transactional
    public void signup(NxUser user) {
        // 1. ID 중복 체크
        if (userMapper.findByUserId(user.getUserId()) != null) {
            throw new BizException("이미 존재하는 ID입니다.");
        }

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(user.getUserPw());
        user.setUserPw(encodedPassword);

        // 3. 기본 권한 설정 (없으면 ROLE_USER)
        if (user.getRoles() == null || user.getRoles().isBlank()) {
            user.setRoles("ROLE_USER");
        }
        
        // 4. 등록자 정보 세팅 (생성 시점의 Audit)
        // BaseEntity가 아닌 직접 필드 관리 형식이므로 수동 주입
        user.setRegId(user.getUserId());

        // 5. 저장
        userMapper.save(user);
    }
}