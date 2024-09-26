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
public class RegisterRequest { // 회원가입 요청시 들어오는 데이터의 틀
    private String id;
    private String password;
    private String role;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsExpired;
    private boolean isEnabled;

    // Entity로 변환하다 ... 암호화된 패스워드로 Entity로 변환(재구성)
    public Mber toEntity(String password) { // 회원가입 요청으로 들어오는 데이터를 비밀번호만 암호화 후 빌드(재구성?이라고 표현하는게 적절할까요?)
        return Mber.builder()
                .id(this.id)
                .password(password) // 이 때 BCrypt 알고리즘으로 암호화된 비밀번호를 넘겨준다.
                .role(this.role)
                .isAccountNonExpired(this.isAccountNonExpired) // 이 이하로는 사실 입력을 받아준다기 보단 그냥 true 값으로 다 넘겨야 하지 않을까한다.
                .isAccountNonLocked(this.isAccountNonLocked) // 왜냐하면 이게 null 값이면 나중에 UserDetail을 상속받았을 때 문제가 생길 수도 .. ?
                .isCredentialsExpired(this.isCredentialsExpired)
                .isEnabled(this.isEnabled)
                .build();
    }
}
