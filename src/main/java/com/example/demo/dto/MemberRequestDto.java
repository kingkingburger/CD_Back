package com.example.demo.dto;


import com.example.demo.entity.Member_table;
import com.example.demo.entity.Usersdata;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class MemberRequestDto {

    private String email; //작성자
    private String passwd; //카테고리


    public Member_table toEntity() {
        return Member_table.builder()
                .email(email)
                .passwd(passwd)
                .build();
    }
}
