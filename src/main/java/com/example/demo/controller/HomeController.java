package com.example.demo.controller;

import com.example.demo.SessionConst;
import com.example.demo.dto.MemberResponseDto;
import com.example.demo.entity.MemberRepository;
import com.example.demo.model.LoginService;
import com.example.demo.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@Slf4j
@RestController
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final LoginService loginService;
    private final SessionManager sessionManager;

    //클라이언트 고유 id 값으로 로그인
    @PostMapping("/check")
    public Object homeLogin(
            @SessionAttribute(name = SessionConst.LOGIN_MEBMER, required = false) Object loginMember) {

        //세션 관리자에 저장된 회원 정보 조회
        //Object session = sessionManager.getSession(request);
        //Object loginedmember = session.getAttribute(SessionConst.LOGIN_MEBMER);

        if(loginMember == null){
            System.out.println("세션값이 null");
            return "세션값이 null";
        }

        //로그인 성공
        return loginMember;
    }

}
