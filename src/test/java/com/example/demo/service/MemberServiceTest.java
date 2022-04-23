package com.example.demo.service;

import com.example.demo.dto.MemberFormDto;
import com.example.demo.repository.MemberRepository;
import com.example.demo.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void findbyLoginid(){
//        Optional<MemberFormDto> byMemberLoginid = memberRepository.findByLoginId("1234");
//        System.out.println("byMemberLoginid = " + byMemberLoginid);
    }

    @Test
    void findAll() {

        List<Member> all = memberRepository.findAll();
        System.out.println("all = " + all);
        //조회
    }
}