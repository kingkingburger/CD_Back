package com.example.demo.model;

import com.example.demo.dto.MemberRequestDto;
import com.example.demo.entity.MemberRepository;
import com.example.demo.entity.Member_table;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Member;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void save() {

        MemberRequestDto memberRequestDto = new MemberRequestDto();
        memberRequestDto.setMemberLoginid("wmh01111");
        memberRequestDto.setMemberName("wonminho");
        memberRequestDto.setMemberPassword("1234");
        memberRequestDto.setMemberPhone("01035862056");

        System.out.println("memberRequestDto = " + memberRequestDto);
        //저장
        memberRepository.save(memberRequestDto.toEntity());

        //조회

    }
}