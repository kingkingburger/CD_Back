package com.example.demo.controller.Home;

import com.example.demo.SessionConst;
import com.example.demo.dto.MemberFormDto;
import com.example.demo.entity.Members;
import com.example.demo.entity.ProductImage;
import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ProductService productService;

    @GetMapping("/")
    public String homeLogin(@SessionAttribute(name = SessionConst.LOGIN_MEBMER, required = false) Members loginMember,
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
     *
     * @param model productImage를 담기위한 매개변수
     * @return
     */
    @GetMapping("/home")
    public String home(Model model) {

        List<ProductImage> allProductImage = productService.findAllProductImage();
        model.addAttribute("allProductImage", allProductImage);

        return "home-electronics-store";
    }



}
