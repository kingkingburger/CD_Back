package com.example.demo.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UsersdataTest {

    @Autowired
    UserdataRepository userdataRepository;

    @Test
    void save(){
        //1. 파라미터 생성
        Usersdata params = Usersdata.builder()
                .category("전자제품")
                .username("구구")
                .price(600000)
                .immediatelyprice(800000)
                .title("갤럭시 s8")
                .location("경기도 수원시")
                .imgsrc("/userimg2.png")
                .deleteYn('N')
                .build();

        //2. 저장
        userdataRepository.save(params);
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
}