package com.example.demo.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Entity
@Data
@Table(name="Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productid; // PK

    private String productName; //물품이름

    private Long auctionPrice; //경매가격
    private Long productPrice; //즉시거래가격

    private String imageNamesrc; //이미지
    private String productExplanation; //물품상세설명
    private String productCategory; //물품카테고리
    private LocalDateTime createdDate = LocalDateTime.now() ; //생성일자

    @ManyToOne
    @JoinColumn(name="memberid") //Product 테이블에 있는 것을 매핑
    private Member_table member_table;

    //빌더 패턴을 이용하면 어떤 멤버에 어떤 값을 세팅하는지 직관적으로 확인이 가능합니다.
    //인자의 순서에 관계없이 객체를 생성할 수 있습니다.
    @Builder
    public Product(String productName, Long auctionPrice, Long productPrice, String imageNamesrc, String productExplanation, String productCategory){
        this.productName = productName;
        this.auctionPrice = auctionPrice;
        this.productPrice = productPrice;
        this.imageNamesrc = imageNamesrc;
        this.productExplanation = productExplanation;
        this.productCategory = productCategory;
    }
}
