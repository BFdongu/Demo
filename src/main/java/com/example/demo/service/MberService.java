package com.example.demo.service;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.Mber;
import com.example.demo.mapper.MberMapper;
import com.example.demo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MberService {

    private final MberMapper mberMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 앤 왜 final을 안 붙히지 ?? 어차피 고정값이기도 한 건데 final을 붙히면 더 좋은 거 아닌가요? ==> 질문
    // @Value는 런타임 시 주입되므로 final을 붙일 수 없음 ==> 아래부터 ChatGPT의 대답
    // secretKey에 @Value 어노테이션을 통해 주입되므로 final을 사용할 수 없습니다.
    // Spring이 실행 시점에 값을 주입하기 때문에 final을 붙이면 에러가 발생합니다.
    // 하지만 다른 고정된 값인 expireTimeMs에는 final을 유지하였습니다.
    @Value("${jwt.secret}")
    private String secretKey;
    private final long expireTimeMs = 1000 * 60 * 60 * 24 * 7; // 토큰 7일

    // 회원가입
    public Mber register(RegisterRequest request) {
        Map<String, Object> check = new HashMap<>();
        check = mberMapper.searchAccount(request.getId()); // check변수로 계정의 아이디와 비밀번호르 받고

        // 만약 있다면 RuntimeException
        if(check == null) {
            Mber saveMber = request.toEntity(request.getPassword()) ; // 맴버 객체로 반환할 saveMber를 만들어야 한다.

            mberMapper.register(request.toEntity(bCryptPasswordEncoder.encode(request.getPassword())));
            return Mber.fromEntity(saveMber);
        }
        else {
            throw new RuntimeException("해당 아이디가 존재합니다.");
        }
    }

    // 로그인
    public String login(String id, String password) {
       Map<String, Object> mber = new HashMap<>();
        mber = mberMapper.searchAccount(id); // 아이디로 계정을 확인해서 없다면 회원가입을 권유, 있다면 정보 저장
        System.out.println(mber);
        System.out.println((String)mber.get("password"));

        if ( !bCryptPasswordEncoder.matches(password, (String)mber.get("password"))) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return JwtUtil.createToken(id, expireTimeMs, secretKey);
    }

    // 모든 계정 조회
    public Map<String, Object> getAllMberAccount() {
        return mberMapper.getAllMberAccount();
    }

    // 아이디로 계정 검색
    public Map<String, Object> searchAccount(String id) {
        return mberMapper.searchAccount(id);
    }
}