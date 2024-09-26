package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig { // Spring Security의 설정을 관리하는 클래스
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // http 보안 설정을 구성, 인증/인가/세션 정책등을 설정
        http
                // http 기본 인증을 비활성화, 기본 인증은 브라우저가 팝업을 통해 자격을 요청하는 방식인데 API 기반 애플리케이션의 경우 불필요한 경우가 많아서 비활성화를 한다. => 그렇다면 왜 API 애플리케이션은 팝업을 통한 자격 요청이 불필요한가?
                // 대답: API 어플리케이션의 경우 사용자와 서버간의 대화로 진행된다. 매번 브라우저를 통해서 팝업을 띄우고 자격을 요청받는 행위는 API 어플리케이션의 불필요한 행위이다.
                .httpBasic().disable()
                // csrf 설정을 비활성화, RESTful API서버에서는 csrf방어가 필요하지 않기 때문에 비활성화 => 왜 필요하지 않는가?
                // csrf는 쿠키 그러나 JWT는 토큰 그러므로 불필요하다.
                .csrf().disable()
                // 세션 관리를 설정하는 부분
                .sessionManagement()
                 // 애플리케이션이 STATELESS 세션 정책을 사용, JWT의 경우 세션을 저장하지 않으므로 아래의 코드가 필수, 클라이언트는 매 요청마다 토큰을 전송하고 서버는 기억하지 않는다.
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // http의 인증/인가 규칙을 설정해주는 코드
                .authorizeHttpRequests(auth -> auth
                        // 아래의 경로로 들어오는 요청은 누구나 접근 가능
                        .requestMatchers("/test/**", "/login", "/register").permitAll()
                        // 아래의 경로로 들어오는 요청은 admin 권한이 있는 사용자만 접근이 가능
                        .requestMatchers("/mber/getAllMberAccount").hasRole("admin")
                        // 그 외의 경로로 들어오는 요청은 인증을 요구한다.
                        .anyRequest().authenticated());

        return http.build(); // 위의 설정대로 http 보안 설정을 빌드한다.
    }

    @Bean
    // 비밀번호를 암호화하기 위한 클래스
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // BCrypt 알고리즘을 사용해서 보안을 강화
}