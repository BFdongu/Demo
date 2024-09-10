package com.example.demo.mapper;


import com.example.demo.dto.Mber;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MberMapper {
    Map<String, Object> searchPassword(String id);

    Map<String, Object> getAllMberAccount();

    void registerAccount(String id, String password);
}
