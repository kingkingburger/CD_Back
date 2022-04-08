package com.example.demo.entity;

import com.example.demo.dto.MemberRequestDto;
import com.example.demo.dto.MemberResponseDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UsersdataTest {

    @Autowired
    UserdataRepository userdataRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void save(){
        //1. 파라미터 생성
        Member_table params = Member_table.builder()
                .memberLoginid("won")
                .memberPassword("1234")
                .memberName("minho")
                .memberPhone("01035862056")
                .build();

        //2. 저장
        memberRepository.save(params);
    }

    @Test
    void findAll(){
        long count = userdataRepository.count();
        List<Usersdata> usersdataList = userdataRepository.findAll();
        System.out.println(usersdataList);
    }

    @Test
    void delete(){

        Usersdata entity = userdataRepository.findById((long) 3).get();

        userdataRepository.delete(entity);
    }
    @Test
    void find(){
        Optional<MemberResponseDto> loginid = memberRepository.findByMemberLoginid("2");
        if(loginid.isPresent()){
            System.out.println("loginid = " + loginid.get());
        }
        else{
            System.out.println("null");
        }
    }


}