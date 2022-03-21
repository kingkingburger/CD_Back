package com.example.demo.dto;

import com.example.demo.entity.Usersdata;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class UserDataResponseDto {


    private Long id; // PK
    private LocalDateTime createdDate ; //생성일
    private LocalDateTime modifiedDate = LocalDateTime.now(); //수정일
    private String category; //카테고리
    private String username; //작성자
    private int price; //경매 가격
    private int immediatelyprice; // 즉시구매가
    private String title; //제목
    private String location; //지역
    private String imgsrc; //이미지 경로
    private char deleteYn; //삭제 여부

    public UserDataResponseDto(Usersdata usersdata) {
        this.id = usersdata.getId();
        this.category = usersdata.getCategory();
        this.username = usersdata.getUsername();
        this.price = usersdata.getPrice();
        this.immediatelyprice = usersdata.getImmediatelyprice();
        this.title = usersdata.getTitle();
        this.location = usersdata.getLocation();
        this.imgsrc = usersdata.getImgsrc();
        this.deleteYn = usersdata.getDeleteYn();
        this.createdDate = usersdata.getCreatedDate();
    }

    @Override
    public String toString() {
        return "UserDataResponseDto{" +
                "id=" + id +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                ", category='" + category + '\'' +
                ", username='" + username + '\'' +
                ", price=" + price +
                ", immediatelyprice=" + immediatelyprice +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                ", imgsrc='" + imgsrc + '\'' +
                ", deleteYn=" + deleteYn +
                '}';
    }

}
