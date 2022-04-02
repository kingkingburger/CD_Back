package com.example.demo.model;

import com.example.demo.dto.MemberRequestDto;
import com.example.demo.dto.MemberResponseDto;
import com.example.demo.dto.UserDataRequestDto;
import com.example.demo.dto.UserDataResponseDto;
import com.example.demo.entity.MemberRepository;
import com.example.demo.entity.Member_table;
import com.example.demo.entity.UserdataRepository;
import com.example.demo.entity.Usersdata;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    //final 붙여야지 생성자 만들어줌
    private final MemberRepository memberRepository;

    @Transactional
    public Long save(final MemberRequestDto params){
        Member_table entity = memberRepository.save(params.toEntity());
        return entity.getUserid();
    }

    //게시글 리스트 조회
    public List<MemberResponseDto> findAll(){
        Sort sort = Sort.by(Sort.Direction.DESC , "userid");
        List<Member_table> list = memberRepository.findAll(sort);

        return list.stream().map(MemberResponseDto::new).collect(Collectors.toList());
    }

}
