package com.nexus.backend.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NxUser implements UserDetails {

    // --- Core Fields ---
    private String userId;      
    private String userPw;      
    private String userNm;      
    private String roles;       

    // --- Audit Fields (여기가 빠져 있었습니다!) ---
    private String regId;        // ★ 추가: 등록자 ID
    private LocalDateTime regDt; // 등록일시
    private String modId;        // ★ 추가: 수정자 ID
    private LocalDateTime modDt; // 수정일시

    // =========================================================
    // Spring Security : UserDetails Implementations
    // =========================================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (roles == null || roles.isBlank()) {
            return Collections.emptyList();
        }
        return Arrays.stream(roles.split(","))
                .map(String::trim)
                .filter(role -> !role.isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override public String getPassword() { return this.userPw; }
    @Override public String getUsername() { return this.userId; }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}