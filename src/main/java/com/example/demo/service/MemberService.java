package com.example.demo.service;

import com.example.demo.dto.MemberRequestDto;
import com.example.demo.dto.MemberResponseDto;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    //final 붙여야지 생성자 만들어줌
    private final MemberRepository memberRepository;

    @Transactional
    public boolean save(final MemberRequestDto params){
        //들어온 id
        String input_loginid = params.getMemberLoginid();

        //만약 DB에 입력값이 없다면
        Optional<MemberResponseDto> checked_duplication = memberRepository.findByMemberLoginid(input_loginid);

        log.info("checked_duplication = {}", checked_duplication);
        if(checked_duplication.isEmpty()){
            memberRepository.save(params.toEntity());
            return true;
        }else{ //DB에 등록이 되었다면 에러를 던저줘야 함
            return false;
        }

    }


}
