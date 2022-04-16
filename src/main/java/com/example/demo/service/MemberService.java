package com.example.demo.service;

import com.example.demo.dto.MemberRequestDto;
import com.example.demo.dto.MemberResponseDto;
import com.example.demo.entity.MemberRepository;
import com.example.demo.entity.Member_table;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    //게시글 리스트 조회
    public List<MemberResponseDto> findAll(){
        Sort sort = Sort.by(Sort.Direction.DESC , "memberid");
        List<Member_table> list = memberRepository.findAll(sort);

        return list.stream().map(MemberResponseDto::new).collect(Collectors.toList());
    }


}
