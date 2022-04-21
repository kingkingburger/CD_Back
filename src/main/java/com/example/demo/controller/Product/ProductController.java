package com.example.demo.controller.Product;


import com.example.demo.SessionConst;
import com.example.demo.dto.MemberResponseDto;
import com.example.demo.entity.Member_table;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping("/addProduct")
    public String addProduct(@ModelAttribute("product")Product product){
        return "addProductForm";
    }

    @PostMapping("/addProduct")
    public String saveProduct(@ModelAttribute("product")Product product, HttpServletRequest request, BindingResult bindingResult){

        if(bindingResult.hasGlobalErrors()){
            bindingResult.reject("producterr","물품을 입력하세요");
            return "addProductForm";
        }

        HttpSession session = request.getSession();
        Member_table loginmember = (Member_table) session.getAttribute(SessionConst.LOGIN_MEBMER);

        log.info("loginmember = {}", loginmember);
        log.info("product = {}", product);

        product.setMember_table(loginmember);
        productRepository.save(product);

        return "redirect:/";
    }

}
