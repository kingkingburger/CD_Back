package com.example.demo.controller;

import com.example.demo.SessionConst;
import com.example.demo.dto.MemberRequestDto;
import com.example.demo.dto.MemberResponseDto;
import com.example.demo.model.LoginService;
import com.example.demo.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;


    @PostMapping("/add")
    public Object save(@Valid @RequestBody MemberRequestDto memberRequestDto, BindingResult bindingResult, HttpServletResponse response)  {
        System.out.println(memberRequestDto.toString());
        if(bindingResult.hasErrors()){
            return bindingResult.getGlobalErrors();
        }
        //입력된 id 중복 체크
        loginService.checkvaild(memberRequestDto);

        //저장
        loginService.save(memberRequestDto);

        //방금 만든 회원 정보로 로그인
        MemberResponseDto member = loginService.login(memberRequestDto.getMemberLoginid(), memberRequestDto.getMemberPassword());


        //세션 관리자를 통해 세션을 생성하고, 회원 데이터를 보관
        sessionManager.createdSession(member, response);

        return member;
    }

    @PostMapping("/login")
    public Object login(@Valid @RequestBody MemberRequestDto memberRequestDto, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) { //에러가 있다면
            return "에러발생";
        }
        System.out.println("memberRequestDto getMemberLoginid = " + memberRequestDto.getMemberLoginid());
        System.out.println("memberRequestDto getMemberPassword = " + memberRequestDto.getMemberPassword());
        MemberResponseDto member = loginService.login(memberRequestDto.getMemberLoginid(), memberRequestDto.getMemberPassword());

        if (member == null) { //id, password둘다 안오면
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다");
            return bindingResult.getGlobalErrors();
        }

        //세션 관리자를 통해 세션을 생성하고, 회원 데이터를 보관
        //sessionManager.createdSession(member, response);

        //로그인 성공 처리
        //세션이 있있으면 있는 세션 반환, 없으면 신규 세션을 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보를 보관
        session.setAttribute(SessionConst.LOGIN_MEBMER, member);
        System.out.println("session = " + session.getId());


        return member;
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request){
//        sessionManager.expire(request);
        HttpSession session = request.getSession(false);
        if(session != null){
            session.invalidate();
        }
    }

    private void expireCookie(HttpServletResponse response ,String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}

