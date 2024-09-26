package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController { // 테스트 관련 컨트롤러

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}
