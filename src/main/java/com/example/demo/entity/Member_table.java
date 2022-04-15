package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Entity
@Data
public class Member_table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberid; // PK
    @NotEmpty
    @Column(unique = true )
    private String memberLoginid;
    @NotEmpty
    private String memberName;
    @NotEmpty
    private String memberPassword;
    private String memberPhone;
    private int memberRank;
    private int memberPerchase;
    private LocalDateTime createdDate = LocalDateTime.now() ;


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
