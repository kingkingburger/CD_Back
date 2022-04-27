package com.example.demo.controller.Product;


import com.example.demo.SessionConst;
import com.example.demo.entity.Category;
import com.example.demo.entity.Members;
import com.example.demo.entity.Product;
import com.example.demo.dto.ProductFormDto;
import com.example.demo.service.ProductService;
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

//    private final ProductRepository productRepository;
//
//    private final Product product;  //product 테이블 기져오기
//    private final Category category;//category 테이블 가져오기

    private final ProductService productService;

    @GetMapping("/addProduct")
    public String addProduct(@ModelAttribute("productData") ProductFormDto productData){
        return "addProductForm";
    }

    @PostMapping("/addProduct")
    public String saveProduct(@ModelAttribute("productData") ProductFormDto productData,
                                HttpServletRequest request,
                                BindingResult bindingResult){

        if(bindingResult.hasGlobalErrors()){
            bindingResult.reject("producterr","물품을 입력하세요");
            return "addProductForm";
        }

        HttpSession session = request.getSession();
        Members loginMember = (Members) session.getAttribute(SessionConst.LOGIN_MEBMER);

        productService.save(productData ,loginMember);

        return "redirect:/";
    }

}
