package com.example.demo.security;

import com.example.demo.dto.Mber;
import com.example.demo.service.MberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class AuthProvider implements AuthenticationProvider {

    @Autowired
    private MberService mberService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id = (String) authentication.getPrincipal(); // 로그인 창에 입력한 id
        String password = (String) authentication.getCredentials(); // 로그인 창에 입력한 password

        PasswordEncoder passwordEncoder = mberService.passwordEncoder();
        UsernamePasswordAuthenticationToken token;
        Map<String, Object> mber = mberService.searchPassword(id);

        if (mber != null && passwordEncoder.matches(password, (String) mber.get("password"))) {
            List<GrantedAuthority> roles = new ArrayList<>();
            roles.add(new SimpleGrantedAuthority("USER"));

            token = new UsernamePasswordAuthenticationToken(mber.get("id"), null, roles);

            return token;
        }

        throw new BadCredentialsException("No such user or wrong password");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
