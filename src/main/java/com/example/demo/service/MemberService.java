package com.example.demo.service;

import com.example.demo.dto.MemberFormDto;
import com.example.demo.entity.Members;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    //final 붙여야지 생성자 만들어줌
    private final MemberRepository memberRepository;

    public void save(MemberFormDto form){

        Members member = Members.builder()
                .loginId(form.getLoginId())
                .name(form.getName())
                .password((form.getPassword()))
                .build();

        memberRepository.save(member);

    }


}
