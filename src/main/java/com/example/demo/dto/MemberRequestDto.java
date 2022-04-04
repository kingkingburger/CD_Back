package com.example.demo.dto;


import com.example.demo.entity.Member_table;
import com.example.demo.entity.Usersdata;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class MemberRequestDto {

    private String email;
    private String passwd;
    private String name;
    private String phone;

    public Member_table toEntity() {
        return Member_table.builder()
                .email(email)
                .passwd(passwd)
                .name(name)
                .phone(phone)
                .build();
    }
}
