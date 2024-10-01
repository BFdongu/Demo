package com.example.demo.controller;

import com.example.demo.entity.Mber;
import com.example.demo.service.MberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mber")
@RequiredArgsConstructor
public class MberController {
    private final MberService mberService;

    @Value("${jwt.secret}")
    private String secretKey;

    // 계정 검색
    @GetMapping("/searchAccount")
    public Map<String, Object> searchPassword(String id) {
        return mberService.searchAccount(id);
    }

    // 맴버 엔티티로 계정 검색
    @GetMapping("/searchAccountWithMber")
    public Mber searchAccountWithMber(String id) {return mberService.searchAccountWithMber(id); }

    // 계정 전체 조회
    @GetMapping("/getAllMberAccount")
    public List<Map<String, Object>> getAllMberAccount(HttpServletRequest request) {
        // Authorization 헤더에서 JWT 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            try {
                // JWT 토큰을 파싱하고 클레임 추출
                Claims claims = Jwts.parser()
                        .setSigningKey(secretKey)  // 시크릿 키를 사용해 토큰 검증
                        .parseClaimsJws(token)
                        .getBody();

                String role = (String) claims.get("role"); // 클레임에서 role 추출
                if (role.equals("ROLE_ADMIN")) {
                    return mberService.getAllMberAccount();
                }
            } catch (Exception e) {

            }
        }
        else {
//            return "No token found in Authorization header.";
        }
        return null; // 앤 왜 널값을 반환해도 괜찮은 거지 ?   
    }
}