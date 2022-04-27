package com.example.demo.controller.Product;


import com.example.demo.SessionConst;
import com.example.demo.entity.Category;
import com.example.demo.entity.Member;
import com.example.demo.entity.Product;
import com.example.demo.dto.ProductDto;
import com.example.demo.repository.ProductRepository;
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
import java.util.Collections;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;

    private final Product product;  //product 테이블 기져오기
    private final Category category;//category 테이블 가져오기

    @GetMapping("/addProduct")
    public String addProduct(@ModelAttribute("productdata") ProductDto productdata){
        return "addProductForm";
    }

    @PostMapping("/addProduct")
    public String saveProduct(@ModelAttribute("productdata") ProductDto productdata,
                              HttpServletRequest request,
                              BindingResult bindingResult, Model model){

        if(bindingResult.hasGlobalErrors()){
            bindingResult.reject("producterr","물품을 입력하세요");
            return "addProductForm";
        }

        HttpSession session = request.getSession();
        Member loginmember = (Member) session.getAttribute(SessionConst.LOGIN_MEBMER);

        product.setMember_table(loginmember); // memberid를 넣는곳
        model.addAttribute("category","1");

        String productdataCategory = productdata.getCategory();// html에서 넘어온 카테고리
        log.info("productdataCategory : {}", productdataCategory);
        String[] split = productdataCategory.split(","); //view에서 ,을 기준으로 카테고리가 넘어온다.

        category.setFirst(split[1]);                        // 1차 카테고리
        category.setSecond(split[0]);                       // 2차 카테고리
        
        product.setCategory(this.category);                             // productdata로 넘어온것을 product table에 넣어주기

        product.setProductName(productdata.getProductName());
        product.setAuctionPrice(productdata.getAuctionPrice());
        product.setProductPrice(productdata.getProductPrice());
        product.setImageNamesrc(productdata.getImageNamesrc());
        product.setProductExplanation(productdata.getProductExplanation());

        this.category.setProductList(Collections.singletonList(product));

        log.info("product : {}", product);

        productRepository.save(product);

        product.setProductid(null);   // jpa에서 save는 @id 값이 null일 때 Entity를 new라고 판단한다.
        category.setCategoryid(null); // 만약 new라고 판단되지 않으면 update문으로 실행된다.

        return "redirect:/";
    }

}
