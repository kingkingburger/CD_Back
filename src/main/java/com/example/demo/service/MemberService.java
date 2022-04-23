package com.example.demo.service;

import com.example.demo.dto.MemberFormDto;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    //final 붙여야지 생성자 만들어줌
    private final MemberRepository memberRepository;

    @Transactional
    public void save(final MemberFormDto form){

        Member member = Member.builder()
                .loginId(form.getLoginId())
                .name(form.getName())
                .password((form.getPassword()))
                .build();

        memberRepository.save(member);

    }


}
