package com.example.demo.controller.Home;

import com.example.demo.SessionConst;
import com.example.demo.dto.MemberFormDto;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.LoginService;
import com.example.demo.service.MemberService;
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

    @GetMapping("/")
    public String homeLogin(@SessionAttribute(name = SessionConst.LOGIN_MEBMER, required = false) Member loginMember,
                            Model model,
                            @ModelAttribute("member") MemberFormDto member) {

        //세션에 회원 데이터가 없으면
        if (loginMember == null) {
            log.info("세션에 회원 없음");
            return "index";
        }

        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "index";
    }

    @GetMapping("/home")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_MEBMER, required = false) Member loginMember,
                       Model model,
                       @ModelAttribute("member") MemberFormDto member) {

        return "home-electronics-store";
    }


}
