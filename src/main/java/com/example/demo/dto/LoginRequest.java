package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor // json파일로 넘겨주는 경우 기본 생성자가 필요함
public class LoginRequest {
    private String id;
    private String password;
}
