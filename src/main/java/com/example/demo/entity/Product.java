package com.example.demo.entity;


import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor(access= AccessLevel.PROTECTED)

@Entity
@Data
@Table
@Component
//@ToString(exclude = "category")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productid; // PK

    private String productName; //물품이름

    private Long auctionPrice; //경매가격
    private Long productPrice; //즉시거래가격

    private String imageNamesrc; //이미지
    private String productExplanation; //물품상세설명

    private LocalDateTime createdDate = LocalDateTime.now() ; //생성일자

    @ManyToOne
    @JoinColumn(name="memberid", nullable=false) //Product 테이블에 있는 것을 매핑 , nullable을 false로 해서 내부 join으로 변경
    private Member member_table;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "categoryid", nullable = false)
    private Category category;


}
