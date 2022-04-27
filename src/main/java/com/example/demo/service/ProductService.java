package com.example.demo.service;

import com.example.demo.SessionConst;
import com.example.demo.dto.ProductFormDto;
import com.example.demo.entity.Category;
import com.example.demo.entity.Members;
import com.example.demo.entity.Product;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void save(ProductFormDto productFormDto, Members loginMember) {

        String categoryName = productFormDto.getCategory();

        Category category = categoryClassification(categoryName);


        Product product = Product.builder().name(productFormDto.getName())
                .instantPrice(productFormDto.getInstancePrice())
                .auctionPrice(productFormDto.getAuctionPrice())
                .imageNameSrc(productFormDto.getImageNameSrc())
                .Explanation(productFormDto.getExplanation())
                .createdDate(productFormDto.getCreatedDate())
                .members(loginMember)
                .build();

        product.changeCategory(category);

        productRepository.save(product);


    }

    private Category categoryClassification( String categoryName ) {

        String[] splitCategory = categoryName.split(",");
        Category category = Category.builder().first(splitCategory[0])
                .second(splitCategory[1])
                .build();

        return category;


    }


}
