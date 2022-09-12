package SilkLoad.controller.Login;

import SilkLoad.SessionConst;
import SilkLoad.config.auth.dto.SessionUser;
import SilkLoad.dto.LoginFormDto;
import SilkLoad.entity.Members;
import SilkLoad.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
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

@Controller
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/loginmember")
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