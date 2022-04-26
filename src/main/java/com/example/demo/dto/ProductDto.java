package com.example.demo.dto;


import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@Component
public class ProductDto {
    private Long productid; // PK
    private String productName; //물품이름
    private Long auctionPrice; //경매가격
    private Long productPrice; //즉시거래가격
    private String category; // 카테고리1
    private String imageNamesrc; //이미지
    private String productExplanation; //물품상세설명
    private LocalDateTime createdDate = LocalDateTime.now() ; //생성일자


}
