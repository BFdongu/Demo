package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Builder
public class Mber implements UserDetails { // 맴버 클래스, DB있는 MBER_ACCOUNT_INFO랑 동일한 내용
    private String id;
    private String password;
    private String role;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsExpired;
    private boolean isEnabled;

    // Entity에서 변환하다 ...
    public static Mber fromEntity(Mber mber) { // 맴버 클래스를 인자로 받아서 맴버를 반환해 주는 건데 .. 애는 왜 있는 건지 잘 모르겠다. toEntity의 경우 비밀번호 암호화라도 하는데 이건 .. 음 ..
        Mber member = Mber.builder() // 애도 사용할 때 주의할 것! UserDetail을 상속할 경우 role 아래에 있는 변수들이 null 값이게 되면 에러가 나오는 경우가 있을 수 있음
                .id(mber.getId())
                .password(mber.getPassword())
                .role(mber.getRole())
                .isAccountNonExpired(mber.isAccountNonExpired())
                .isAccountNonLocked(mber.isAccountNonLocked())
                .isCredentialsExpired(mber.isCredentialsExpired())
                .isEnabled(mber.isEnabled())
                .build();
        return member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsExpired;
    }
}
