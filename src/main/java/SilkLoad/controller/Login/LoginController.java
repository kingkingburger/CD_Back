package SilkLoad.controller.Login;

import SilkLoad.SessionConst;
import SilkLoad.dto.LoginFormDto;
import SilkLoad.entity.Members;
import SilkLoad.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/loginMember")
    public String loginForm(@ModelAttribute("loginFormDto") LoginFormDto loginFormDto) {

        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginFormDto") LoginFormDto loginFormDto,
                        BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request,
                        Model model) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        Members loginMember = loginService.login(loginFormDto.getLoginId(), loginFormDto.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login";
        }

        HttpSession session = request.getSession();

        //쿠키 이름: jsessionid, 값: uuid, uuid를 통해 session 속성에 접근
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        //권한 부여: memberDTO의 getAuthorities() 메소드에서 담당.
        List<GrantedAuthority> roles = (List<GrantedAuthority>) loginMember.getAuthorities();
        // 스프링 시큐리티 내부 클래스로 인증 토큰 생성
        UsernamePasswordAuthenticationToken LoginSuccessToken = new UsernamePasswordAuthenticationToken(loginMember.getLoginId(),
                loginMember.getPassword(), roles);


        // 시큐리티 컨텍스트 객체를 얻습니다.
        SecurityContext context = SecurityContextHolder.getContext();
        // 강제로 인증객체에 ROLE=ROLE_GUEST 주입
        context.setAuthentication(LoginSuccessToken);

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