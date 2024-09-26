package com.example.demo.mapper;

import com.example.demo.entity.Mber;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MberMapper { // 매퍼 인터페이스
    Map<String, Object> searchAccount(String id);

    List<Map<String, Object>> getAllMberAccount();

    Mber searchAccountWithMber(String id);

    void register(Mber mber);
}
