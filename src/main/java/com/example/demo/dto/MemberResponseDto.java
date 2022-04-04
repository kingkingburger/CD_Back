package com.example.demo.dto;

import com.example.demo.entity.Member_table;
import com.example.demo.entity.Usersdata;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class MemberResponseDto {


    private Long userid; // PK
    private String email;
    private String name;
    private String passwd;
    private String phone;
    private LocalDateTime join_date;

    public MemberResponseDto(Member_table member_table) {
        this.userid = member_table.getUserid();
        this.email = member_table.getEmail();
        this.name = member_table.getName();
        this.passwd = member_table.getPasswd();
        this.phone = member_table.getPhone();
        this.join_date = member_table.getJoin_date();

    }

}
