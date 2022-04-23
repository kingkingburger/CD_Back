package com.example.demo.service;

import com.example.demo.repository.MemberRepository;
import com.example.demo.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(String loginId, String password) {

        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            return member.getPassword().equals(password) ? member : null;
        }
        return null;
    }

}
