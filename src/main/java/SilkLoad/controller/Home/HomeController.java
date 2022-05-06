package SilkLoad.controller.Home;


import SilkLoad.SessionConst;
import SilkLoad.dto.LoginFormDto;
import SilkLoad.dto.MemberFormDto;
import SilkLoad.entity.Members;
import SilkLoad.entity.Product;
import SilkLoad.entity.ProductImage;
import SilkLoad.service.MemberService;
import SilkLoad.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;

    @GetMapping("/")
    public String homeLogin(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Members loginMember,
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

    /**
     * @param model product들을 담기위한 매개변수
     * @return model List형태로 view에 반환
     */

    @GetMapping("/home")
    public String home(Model model) {
        List<Product> allProduct = productService.findAllProduct();
        model.addAttribute("Product", allProduct);
        return "home";
    }

}
