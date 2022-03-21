package com.example.demo.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Entity
public class Usersdata {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // PK
    private LocalDateTime createdDate = LocalDateTime.now() ; //생성일
    private LocalDateTime modifiedDate ; //수정일
    private String category; //카테고리
    private String username; //작성자
    private int price; //경매 가격
    private int immediatelyprice; // 즉시구매가
    private String title; //제목
    private String location; //지역
    private String imgsrc; //이미지 경로
    private char deleteYn; //삭제 여부

    //빌더 패턴을 이용하면 어떤 멤버에 어떤 값을 세팅하는지 직관적으로 확인이 가능합니다.
    //인자의 순서에 관계없이 객체를 생성할 수 있습니다.
    @Builder
    public Usersdata(String title, String category, String username, int price, int immediatelyprice,
                     String location, String imgsrc, char deleteYn){
        this.category = category;
        this.username = username;
        this.price = price;
        this.immediatelyprice = immediatelyprice;
        this.title = title;
        this.location = location;
        this.imgsrc = imgsrc;
        this.deleteYn = deleteYn;

    }

    //트랜젝션이 종료(commit)될 때자동으로 쿼리 실행
    public void update(String title, String username, int price, int immediatelyprice){
        this.title = title; //제목
        this.username = username; // 작성자
        this.price = price; //경매 가격
        this.immediatelyprice = immediatelyprice; // 즉시구매가
        this.modifiedDate = LocalDateTime.now(); //수정일
    }

}
