package SilkLoad.controller.Member;

import SilkLoad.SessionConst;
import SilkLoad.dto.MemberFormDto;
import SilkLoad.dto.ProductFormDto;
import SilkLoad.entity.Members;
import SilkLoad.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MemberService memberService;

    @GetMapping("/MyPage")
    public String MyPage(@ModelAttribute("memberFormDto")MemberFormDto memberFormDto) {

        return "myPage";

    }

    @PostMapping("/MyPage")
    public String MyPageModify(@ModelAttribute("memberFormDto")MemberFormDto memberFormDto, BindingResult bindingResult) {

        Members members = memberService.updatePassword(memberFormDto);
        log.info("members={}",members);
        if ( members == null ) {

            bindingResult.rejectValue("loginId","nonExistence");

        }

        return "myPage";
    }


    @GetMapping("/account-wishlist")
    public String Wishlist(@ModelAttribute("productData") ProductFormDto productData) {

        return "account-wishlist";
    }
    @GetMapping("/account-orders")
    public String Orders(@ModelAttribute("productData") ProductFormDto productData) {
        return "account-orders";
    }


}
