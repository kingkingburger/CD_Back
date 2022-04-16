package com.example.demo.controller;

import com.example.demo.SessionConst;
import com.example.demo.dto.MemberRequestDto;
import com.example.demo.entity.MemberRepository;
import com.example.demo.service.LoginService;
import com.example.demo.service.MemberService;
import com.example.demo.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/")
    public String homeLogin(@SessionAttribute(name = SessionConst.LOGIN_MEBMER, required = false) MemberRequestDto loginMember, Model model, @ModelAttribute("member") MemberRequestDto member) {

        //세션에 회원 데이터가 없으면
        if (loginMember == null) {
            return "index";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "index";
    }

    @GetMapping("/home")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEBMER, required = false) MemberRequestDto loginMember, Model model, @ModelAttribute("member") MemberRequestDto member) {

        return "home-electronics-store";
    }


//    @GetMapping("/home")
//    public String home(Model model){
//        return "home";
//    }
}
