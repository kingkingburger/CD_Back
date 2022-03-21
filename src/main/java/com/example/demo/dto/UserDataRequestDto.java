package com.example.demo.dto;


import com.example.demo.entity.Usersdata;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class UserDataRequestDto {

    private String title; // 제목
    private String username; //작성자
    private String category; //카테고리
    private String location; //지역
    private String imgsrc; //이미지 경로
    private int price; //경매 가격
    private int immediatelyprice; // 즉시구매가
    private char deleteYn; //삭제 여부

    public Usersdata toEntity() {
        return Usersdata.builder()
                .title(title)
                .username(username)
                .category(category)
                .location(location)
                .imgsrc(imgsrc)
                .price(price)
                .immediatelyprice(immediatelyprice)
                .deleteYn(deleteYn)
                .build();
    }
}
