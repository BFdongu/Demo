package com.example.demo.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @Value("${jwt.secret}")
    private String secretKey;

        @GetMapping("/hello")
        public String hello() {
            return "hello";
        }

        @GetMapping("/check-token")
        public String checkToken(HttpServletRequest request) {
        // Authorization 헤더에서 JWT 토큰 추출
        String authorizationHeader = request.getHeader("Authorization");

        // 토큰값 출력 (단순히 로그나 콘솔 출력)
        System.out.println("Authorization Header: " + authorizationHeader);

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // "Bearer " 이후 토큰 값만 추출
            try {
                // JWT 토큰을 파싱하고 클레임 추출
                Claims claims = Jwts.parser()
                        .setSigningKey(secretKey)  // 시크릿 키를 사용해 토큰 검증
                        .parseClaimsJws(token)
                        .getBody();

                // 클레임에서 id와 role 추출
                String id = (String) claims.get("id"); // "id" 클레임에서 사용자 ID 추출
                String role = (String) claims.get("role"); // "role" 클레임에서 역할(Role) 추출

                // id와 역할 출력
                System.out.println("ID: " + id);
                System.out.println("Role: " + role);

                // 응답으로 id와 역할 반환
                return "ID: " + id + ", Role: " + role;
            } catch (Exception e) {
                return "Invalid or expired token.";
            }
        } else {
            return "No token found in Authorization header.";
        }
    }
}