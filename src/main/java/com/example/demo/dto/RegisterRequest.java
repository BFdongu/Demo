package com.example.demo.dto;

import com.example.demo.entity.Mber;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class RegisterRequest {
    private String id;
    private String password;
    private String role;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsExpired;
    private boolean isEnabled;

    public Mber toEntity(String password) {
        return Mber.builder()
                .id(this.id)
                .password(password)
                .role(this.role)
                .isAccountNonExpired(this.isAccountNonExpired)
                .isAccountNonLocked(this.isAccountNonLocked)
                .isCredentialsExpired(this.isCredentialsExpired)
                .isEnabled(this.isEnabled)
                .build();
    }
}
