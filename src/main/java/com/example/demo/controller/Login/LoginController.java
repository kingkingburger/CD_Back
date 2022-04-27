package com.example.demo.controller.Login;

import com.example.demo.SessionConst;
import com.example.demo.dto.LoginFormDto;
import com.example.demo.entity.Members;
import com.example.demo.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginFormDto") LoginFormDto loginFormDto) {
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginFormDto") LoginFormDto loginFormDto,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request) {
        log.info("loginFormDto : {}", loginFormDto);

        if( bindingResult.hasErrors()) {
            return  "login";
        }
        Members loginMember = loginService.login(loginFormDto.getLoginId(), loginFormDto.getPassword());

        if( loginMember == null ) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login";
        }

        HttpSession session = request.getSession();

        //쿠키 이름: jsessionid, 값: uuid, uuid를 통해 session 속성에 접근
        session.setAttribute(SessionConst.LOGIN_MEBMER, loginMember);

        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

}

