package com.example.demo.model;

import com.example.demo.dto.MemberResponseDto;
import com.example.demo.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     * @return null 로그인 실패
     */
    public MemberResponseDto login(String loginId, String passwd) {
//        Optional<MemberResponseDto> findMemeberOptional = memberRepository.findByMemberLoginid(loginId);
//        MemberResponseDto member = findMemeberOptional.get();
//        if(member.getMemberPassword().equals(passwd)){
//            return member;
//        }
//        else{
//            return null;
//        }
        return memberRepository.findByMemberLoginid(loginId) //회원이 있는지 없는지 확인
                .filter(m -> m.getMemberPassword().equals(passwd)).orElse(null);
    }
}
