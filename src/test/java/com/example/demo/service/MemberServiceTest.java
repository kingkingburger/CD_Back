package com.example.demo.service;

import com.example.demo.dto.MemberRequestDto;
import com.example.demo.entity.MemberRepository;
import com.example.demo.entity.Member_table;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void findAll() {

        List<Member_table> all = memberRepository.findAll();
        System.out.println("all = " + all);
        //조회

    }
}