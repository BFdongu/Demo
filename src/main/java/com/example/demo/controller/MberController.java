package com.example.demo.controller;

import com.example.demo.service.MberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/mber")
public class MberController {

    @Autowired
    private MberService mberService;

    public MberController(MberService mberService) {
        this.mberService = mberService;
    }

    // 계정 검색
    @GetMapping("/searchAccount")
    public Map<String, Object> searchPassword(String id) {
        return mberService.searchAccount(id);
    }

    // 계정 전체 조회
    @GetMapping("/getAllMberAccount")
    public Map<String, Object> getAllMberAccount() {
        return mberService.getAllMberAccount();
    }
}