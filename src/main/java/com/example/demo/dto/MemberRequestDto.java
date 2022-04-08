package com.example.demo.dto;


import com.example.demo.entity.Member_table;
import com.example.demo.entity.Usersdata;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class MemberRequestDto {

    private String memberLoginid;
    private String memberName;
    private String memberPassword;
    private String memberPhone;

    public Member_table toEntity() {
        return Member_table.builder()
                .memberLoginid(memberLoginid)
                .memberPassword(memberPassword)
                .memberName(memberName)
                .memberPhone(memberPhone)
                .build();
    }

    @Override
    public String toString() {
        return "MemberRequestDto{" +
                "memberLoginid='" + memberLoginid + '\'' +
                ", memberName='" + memberName + '\'' +
                ", memberPassword='" + memberPassword + '\'' +
                ", memberPhone='" + memberPhone + '\'' +
                '}';
    }
}
