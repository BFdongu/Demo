package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class Mber {
    private String id;
    private String password;
    private String role;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsExpired;
    private boolean isEnabled;

    public static Mber fromEntity(Mber mber) {
        Mber member = Mber.builder()
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
}
