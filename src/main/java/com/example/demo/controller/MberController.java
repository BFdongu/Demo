package com.example.demo.controller;

import com.example.demo.dto.Mber;
import com.example.demo.service.MberService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/test/mber")
public class MberController {

    private final MberService mberService;

    public MberController(MberService mberService) {
        this.mberService = mberService;
    }

//    @GetMapping("/login")
//    public String loginPage() { // 로그인되지 않은 상태이면 로그인 페이지를, 로그인된 상태이면 home 페이지를 보여줌
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication instanceof AnonymousAuthenticationToken)
//            return "loginPage";
//        return "redirect:/hello";
//    }

    // 비밀번호 검색
    @GetMapping("/searchPassword")
    public Map<String, Object> searchPassword(String id) {
        return mberService.searchPassword(id);
    }

    // 계정 전체 조회
    @GetMapping("/getAllMberAccount")
    public Map<String, Object> getAllMberAccount() {
        return mberService.getAllMberAccount();
    }

    // 계정 등록
    @PostMapping("/register")
    public void registerAccount(String id, String password) {
        mberService.registerAccount(id, password);
    }



}