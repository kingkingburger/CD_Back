package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@ToString(exclude = "ProductList")
@Entity
@Data
public class Member_table {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberid; // PK

    private String memberLoginid;

    private String memberName;

    private String memberPassword;

    private String memberPhone;

    private int memberRank;

    private int memberPerchase;
    private LocalDateTime createdDate = LocalDateTime.now() ;

    @OneToMany(mappedBy = "member_table", fetch=FetchType.EAGER)
    private List<Product> ProductList = new ArrayList<Product>();

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
