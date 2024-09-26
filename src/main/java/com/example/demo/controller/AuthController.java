package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.dto.RegisterResponse;
import com.example.demo.entity.Mber;
import com.example.demo.service.MberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthController {
    private final MberService mberService;

    @PostMapping("/register") // 회원가입
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        try {
            Mber mber = mberService.register(request); // RegisterReqest라는 정해진 틀데로 들어온 request를 mberService의 register로직을 이용해서 Mber 객체를 생성한다.
            return new ResponseEntity<>(new RegisterResponse(mber.getId()), HttpStatus.OK); // 위에서 생성된 mber 객체와 200 응답을 리스폰스 엔티티에 담아서 반환한다.
        } catch (Exception e) { // 에러 송출 코드
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login") // 로그인
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            String token = mberService.login(request.getId(), request.getPassword()); // LoginRequest라는 틀데로 들어온 request를 mberService의 login로직으로 이동한다. 해당 로직의 경우 JWT 토큰을 반환해 준다.
            return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK); // 반환된 토큰값과 200 응답을 리스폰스 엔티티에 담아서 반환한다.
        } catch (Exception e) { // 에러 송출 코드
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
