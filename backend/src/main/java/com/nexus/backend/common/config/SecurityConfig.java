package com.nexus.backend.common.config;

import com.nexus.backend.common.security.jwt.JwtAccessDeniedHandler;
import com.nexus.backend.common.security.jwt.JwtAuthenticationEntryPoint;
import com.nexus.backend.common.security.jwt.JwtAuthenticationFilter;
import com.nexus.backend.common.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    // 비밀번호 암호화 모듈 (BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. CSRF 비활성화 (Rest API는 필요 없음)
            .csrf(AbstractHttpConfigurer::disable)
            
            // 2. 기본 로그인 폼 & HttpBasic 비활성화
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)

            // 3. 세션 사용 안 함 (STATELESS)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 4. 예외 처리 핸들러 등록
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
            )

            // 5. 요청 URL별 권한 설정
            .authorizeHttpRequests(auth -> auth
                // [허용] 로그인, 회원가입, 정적 리소스는 누구나 접근 가능
            	.requestMatchers("/api/v1/auth/**", "/static/**", "/error").permitAll()
                // [제한] 그 외 모든 요청은 인증 필요
                .anyRequest().authenticated()
            )

            // 6. JWT 필터 등록 (UsernamePasswordAuthenticationFilter 앞에 배치)
            .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}