package com.example.demo.controller;

import com.example.demo.dto.UserDataResponseDto;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorCode;
import com.example.demo.model.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/apitest")
@RequiredArgsConstructor
public class UserdataController {

    private final UserService userService;

    @GetMapping("/test")
    public String test(){
        throw new CustomException(ErrorCode.POSTS_NOT_FOUND);
    }

    @GetMapping("/b")
    public List<UserDataResponseDto> findAll(){
        return userService.findAll();
    }



}
