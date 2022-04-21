package com.example.demo.service;

import com.example.demo.dto.MemberRequestDto;
import com.example.demo.dto.MemberResponseDto;
import com.example.demo.repository.MemberRepository;
import com.example.demo.entity.Member_table;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     * @return null 로그인 실패
     */
    public Member_table login(String loginId, String passwd) {
        return memberRepository.findByMemberLoginid(loginId) //회원이 있는지 없는지 확인
                .filter(m->m.getMemberPassword().equals(passwd))
                .orElse(null);
    }

    @Transactional
    public MemberResponseDto findById(final Long id) {
        Member_table entity = memberRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        return new MemberResponseDto(entity);
    }
    
    //유저 loginid로 유저를 찾아줌
    public Optional<MemberResponseDto> findByLoginId(String loginId) {
        return findAll().stream()
                .filter(m -> m.getMemberLoginid().equals(loginId)).findFirst();
    }


    //테이블에서 전체 유저를 찾아주는 서비스
    public List<MemberResponseDto> findAll(){
        MemberResponseDto userlist = (MemberResponseDto) memberRepository.findAll();
        return (List<MemberResponseDto>) userlist;
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
