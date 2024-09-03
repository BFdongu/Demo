package com.example.demo.service;

import com.example.demo.dto.Mber;
import com.example.demo.mapper.MberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MberService {

    private final MberMapper mberMapper;

    @Autowired
    public MberService(MberMapper mberMapper) {
        this.mberMapper = mberMapper;
    }

    public Map<String, Object> findPassword() {
        return mberMapper.searchPassword("admin");
    }

    public Map<String, Object> getAllMberAccount() {
        return mberMapper.getAllMberAccount();
    }
}
