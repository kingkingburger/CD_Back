package com.example.demo.entity;


import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor(access= AccessLevel.PROTECTED)

@Entity
@Data
@Builder
@AllArgsConstructor
//@ToString(exclude = "category")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id; // PK

    private String name; //물품이름

    private Long auctionPrice; //경매가격
    private Long instantPrice; //즉시거래가격

    private String imageNameSrc; //이미지
    private String Explanation; //물품상세설명

    private LocalDateTime createdDate = LocalDateTime.now() ; //생성일자

    @ManyToOne
    @JoinColumn(name="MEMBER_ID", nullable=false) //Product 테이블에 있는 것을 매핑 , nullable을 false로 해서 내부 join으로 변경
    private Members members;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;

    //양방향 연관관계 객체간에 관계 유지, toString 무한 호출 조심
   public void changeCategory(Category category ) {
        this.category = category;
        category.getProductList().add(this);
    }


}
