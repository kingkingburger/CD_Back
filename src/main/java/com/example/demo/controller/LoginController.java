package com.example.demo.controller;

import com.example.demo.dto.MemberRequestDto;
import com.example.demo.dto.MemberResponseDto;
import com.example.demo.model.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@CrossOrigin(origins = "*")
@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@RequestBody MemberRequestDto memberRequestDto) {
        return "/login";
    }


    @PostMapping("/login")
    public Object login(@Valid @RequestBody MemberRequestDto memberRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { //에러가 있다면
            return "에러발생";
        }
        System.out.println("memberRequestDto getMemberLoginid = " + memberRequestDto.getMemberLoginid());
        System.out.println("memberRequestDto getMemberPassword = " + memberRequestDto.getMemberPassword());
        MemberResponseDto member = loginService.login(memberRequestDto.getMemberLoginid(), memberRequestDto.getMemberPassword());

        if (member == null) { //id, password둘다 안오면
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다");
            System.out.println(bindingResult.getGlobalErrors());

            return bindingResult.getGlobalErrors();
        }

        //로그인 성공 처리
        return "/";
    }
}

