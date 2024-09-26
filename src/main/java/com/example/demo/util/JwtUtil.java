package com.example.demo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {
    public static String createToken(String id, String role, long expireTimeMs, String key) { // 토큰을 생성하는 메소드
        Claims claims = Jwts.claims(); // JWT의 페이로드에 담길 정보
        claims.put("id", id);
        claims.put("role", role);

        return Jwts.builder() // 빌더타입으로 생성을 해줄건데
                .setClaims(claims) // 우선 claim을 넣고
                .setIssuedAt(new Date(System.currentTimeMillis())) // JWT가 생성된 시간
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs)) //JWT의 만료 시간
                .signWith(SignatureAlgorithm.HS256, key) // 마지막으로 HS256 알고리즘과 key값을 가지고 JWT 토큰을 서명한다.
                .compact(); // 마지막으로 문자열 형식으로 압축해서 반환
    }

    public static String validateToken() {
        return "";
    }
}