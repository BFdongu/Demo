package com.example.demo.service;

import com.example.demo.dto.Mber;
import com.example.demo.mapper.MberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MberService {

    @Autowired
    private final MberMapper mberMapper;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public MberService(MberMapper mberMapper) {
        this.mberMapper = mberMapper;
    }

    public Map<String, Object> searchPassword(String id) {
        return mberMapper.searchPassword(id);
    }

    public Map<String, Object> getAllMberAccount() {
        return mberMapper.getAllMberAccount();
    }

    public void registerAccount(String id, String password) {
        mberMapper.registerAccount(id, password);
    }

    public PasswordEncoder passwordEncoder() {
        return this.passwordEncoder;
    }
}
