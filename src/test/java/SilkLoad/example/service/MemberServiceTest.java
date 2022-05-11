package SilkLoad.example.service;


import SilkLoad.dto.MemberFormDto;
import SilkLoad.entity.Members;
import SilkLoad.repository.MemberRepository;
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
        Optional<Members> byId = memberRepository.findById((long) 1);
//        byId.isPresent(member -> new MemberFormDto().
//                builder().
//                loginId(member).
//                build())
        System.out.println("byId = " + byId);
    }

    @Test
    void findAll() {
        List<Members> all = memberRepository.findAll();
        System.out.println("all = " + all);
        //조회
    }


}