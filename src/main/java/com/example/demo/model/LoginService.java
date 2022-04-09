package com.example.demo.model;

import com.example.demo.dto.MemberRequestDto;
import com.example.demo.dto.MemberResponseDto;
import com.example.demo.entity.MemberRepository;
import com.example.demo.entity.Member_table;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     * @return null 로그인 실패
     */
    public MemberResponseDto login(String loginId, String passwd) {
        return memberRepository.findByMemberLoginid(loginId) //회원이 있는지 없는지 확인
                .filter(m -> m.getMemberPassword().equals(passwd)).stream().findFirst().orElse(null);
    }

    //
    @Transactional
    public MemberResponseDto findById(final Long id) {
        Member_table entity = memberRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        return new MemberResponseDto(entity);
    }

    @Transactional
    public Long save(final MemberRequestDto params) {
        System.out.println(params.toString());

        return memberRepository.save(params.toEntity()).getMemberid();

    }

    //중복된 id 체크
    @Transactional
    public void checkvaild(final MemberRequestDto params) {
        boolean b = memberRepository.existsByMemberLoginid(params.getMemberLoginid());
        if (b) {
            throw new IllegalStateException("이미 존재하는 id 입니다.");
        }
    }
}
