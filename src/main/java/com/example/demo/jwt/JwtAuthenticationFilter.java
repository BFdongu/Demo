package com.example.demo.jwt;

import com.example.demo.service.MberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;

@Component
//@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secretKey;
    @Autowired
    @Lazy
    private MberService mberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization"); // 헤더의 정보를 받아오기
        String token = null; // 토큰값을 읽어올 변수
        String id = null; // 아이디값을 읽어올 변수

        if (authHeader != null && authHeader.startsWith("Bearer ")) { // 토큰의 정보를 받아오기 위해서 Bearer 이라고 시작하는
            token = authHeader.substring(7); // 시작 인덱스 7부터 서브스트링에 토큰이 담겨있으므로
            id = extractUsername(token); // 토큰에서 아이디를 추출
        }

        if(id != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 이제 인증을 넣어줄껀데 그러기 위해서는 엔티티의 속성이 바뀌어야 한다.
            // Mber를 userDetail를 상속받는 친구로 만들어줘야 할 것 같다.
            UserDetails userDetails = this.mberService.searchAccountWithMber(id);

            if(validateToken(token, userDetails)) { // 토큰 검증하는 부분 주석 달것
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                System.out.println(authenticationToken);

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                System.out.println(SecurityContextHolder.getContext());
            }
        }

        // 필터 체인 계속 진행
        filterChain.doFilter(request, response);
    }

    // JWT 토큰에서 사용자 이름을 추출
    private String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return (String) claims.get("id"); // JWT의 subject에 사용자 이름을 저장했다면 그 값을 가져옴
    }

    // JWT 토큰 검증
    private boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // JWT 토큰의 만료 여부 확인
    private boolean isTokenExpired(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().before(new Date());
    }
}
