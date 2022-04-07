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
public class Member_table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberid; // PK
    private String memberLoginid;
    private String memberName;
    private String memberPassword;
    private int memberRank;
    private int memberPerchase;
    private String memberPhone;
    private LocalDateTime join_date = LocalDateTime.now() ;


    //빌더 패턴을 이용하면 어떤 멤버에 어떤 값을 세팅하는지 직관적으로 확인이 가능합니다.
    //인자의 순서에 관계없이 객체를 생성할 수 있습니다.
    @Builder
    public Member_table(String memberLoginid, String memberName, String memberPassword, String memberPhone){
        this.memberLoginid = memberLoginid;
        this.memberName = memberName;
        this.memberPassword = memberPassword;
        this.memberPhone =memberPhone;
    }


}
