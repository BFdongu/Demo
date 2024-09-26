package com.example.demo.service;

import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.Mber;
import com.example.demo.mapper.MberMapper;
import com.example.demo.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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
        check = mberMapper.searchAccount(request.getId()); // check변수로 계정의 아이디와 비밀번호를 받고

        if(check == null) { // check 변수가 null이라면 계정을 생성할수 있다는 의미이므로 계정 생성하기
            Mber saveMber = request.toEntity(bCryptPasswordEncoder.encode(request.getPassword())); // 회원가입 요청이 들어온 request를 맴버 엔티티로 변환해준다. 이 때, 아마 세붓사항들이 전부 null 값으로 조정될 것이다.
            mberMapper.register(saveMber); // 매버로 넘겨준다.
            return saveMber;
        }
        else {
            throw new RuntimeException("해당 아이디가 존재합니다.");
        }
    }

    // 로그인
    public String login(String id, String password) {
        Map<String, Object> mber = new HashMap<>();
        mber = mberMapper.searchAccount(id); // 아이디로 계정을 찾아서 정보를 map형식으로 받는다.

        if ( !bCryptPasswordEncoder.matches(password, (String)mber.get("password"))) { // 만약 비밀번호가 다른 시 런타임 오류 발생
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return JwtUtil.createToken(id, (String) mber.get("role"), expireTimeMs, secretKey); // 토큰을 발급
    }

    // 모든 계정 조회
    public List<Map<String, Object>> getAllMberAccount() {
        return mberMapper.getAllMberAccount();
    }

    // 아이디로 계정 검색
    public Map<String, Object> searchAccount(String id) {
        return mberMapper.searchAccount(id);
    }
}