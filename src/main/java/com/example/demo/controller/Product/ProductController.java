package com.example.demo.controller.Product;


import com.example.demo.SessionConst;
import com.example.demo.entity.Category;
import com.example.demo.entity.Member;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductData;
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
import java.util.Collections;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    private final Product product;  //product 테이블 기져오기
    private final Category category;//category 테이블 가져오기

    @GetMapping("/addProduct")
    public String addProduct(@ModelAttribute("productdata")ProductData productdata){
        return "addProductForm";
    }

    @PostMapping("/addProduct")
    public String saveProduct(@ModelAttribute("productdata") ProductData productdata,
                                HttpServletRequest request,
                                BindingResult bindingResult){

        if(bindingResult.hasGlobalErrors()){
            bindingResult.reject("producterr","물품을 입력하세요");
            return "addProductForm";
        }

        HttpSession session = request.getSession();
        Member loginmember = (Member) session.getAttribute(SessionConst.LOGIN_MEBMER);

        product.setMember_table(loginmember); // memberid를 넣는곳

        String productCategory = productdata.getProductCategory(); // html에서 넘어온 카테고리

        category.setFirst(productCategory);                        // 1차 카테고리
        category.setSecond(productCategory);                       // 2차 카테고리
        
        
        product.setCategory(category);                             // productdata로 넘어온것을 product table에 넣어주기

        product.setProductName(productdata.getProductName());
        product.setAuctionPrice(productdata.getAuctionPrice());
        product.setProductPrice(productdata.getProductPrice());
        product.setImageNamesrc(productdata.getImageNamesrc());
        product.setProductExplanation(productdata.getProductExplanation());

        category.setProductList(Collections.singletonList(product));

        log.info("product : {}", product);

        productRepository.save(product);

        product.setProductid(null);   // jpa에서 save는 @id 값이 null일 때 Entity를 new라고 판단한다.
        category.setCategoryid(null); // 만약 new라고 판단되지 않으면 update문으로 실행된다.
        log.info("data fram = {}", category);
        return "redirect:/";
    }

}
