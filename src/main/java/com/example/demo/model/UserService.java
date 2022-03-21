package com.example.demo.model;

import com.example.demo.dto.UserDataRequestDto;
import com.example.demo.dto.UserDataResponseDto;
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
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    //final 붙여야지 생성자 만들어줌
    private final UserdataRepository userdataRepository;

    @Transactional
    public Long save(final UserDataRequestDto params){
        Usersdata entity = userdataRepository.save(params.toEntity());
        return entity.getId();
    }

    //게시글 리스트 조회
    public List<UserDataResponseDto> findAll(){
        Sort sort = Sort.by(Sort.Direction.DESC , "id", "createdDate");
        List<Usersdata> list = userdataRepository.findAll(sort);


        System.out.println(list.stream()
                .map(UserDataResponseDto::new)
                .collect(Collectors.toList())
                );


        return list.stream().map(UserDataResponseDto::new).collect(Collectors.toList());
    }

    public Long update(final Long id, @RequestBody final Usersdata params){
        Usersdata usersdata = userdataRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
        usersdata.update(params.getTitle(), params.getUsername(), params.getPrice(), params.getImmediatelyprice());
        return id;
    }


}
