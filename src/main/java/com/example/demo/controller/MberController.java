package com.example.demo.controller;

import com.example.demo.dto.Mber;
import com.example.demo.service.MberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/test")
public class MberController {

    private final MberService mberService;

    public MberController(MberService mberService) {
        this.mberService = mberService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello";
    }

    @GetMapping("/mber/findPassword")
    public Map<String, Object> findPassword() {
        return mberService.findPassword();
    }

    @GetMapping("/mber/getAllMberAccount")
    public Map<String, Object> getAllMberAccount() {
        return mberService.getAllMberAccount();
    }


}