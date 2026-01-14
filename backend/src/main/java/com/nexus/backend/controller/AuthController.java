package com.nexus.backend.controller;

import com.nexus.backend.common.api.ApiResponse;
import com.nexus.backend.domain.user.NxLoginRequest;
import com.nexus.backend.domain.user.NxUser;
import com.nexus.backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 로그인 API
     * @return JWT Access Token
     */
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody @Valid NxLoginRequest request) {
        log.info("NEXUS [AUTH] - Login Request: {}", request.getUserId());
        String token = authService.login(request);
        return ApiResponse.success(token, "로그인에 성공했습니다.");
    }

    /**
     * 회원가입 API (테스트용)
     */
    @PostMapping("/signup")
    public ApiResponse<Void> signup(@RequestBody @Valid NxUser user) {
        log.info("NEXUS [AUTH] - Signup Request: {}", user.getUserId());
        authService.signup(user);
        return ApiResponse.success(null, "회원가입이 완료되었습니다.");
    }
}