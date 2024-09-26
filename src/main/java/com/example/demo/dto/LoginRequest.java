package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor // json파일로 넘겨주는 경우 기본 생성자가 필요함
public class LoginRequest { // 로그인 요청시 보내는 데이터의 틀
    private String id;
    private String password;
}
