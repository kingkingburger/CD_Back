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

    private Long memberid; // PK
    private String memberLoginid;
    private String memberName;
    private String memberPassword;
    private int memberRank;
    private int memberPerchase;
    private String memberPhone;
    private LocalDateTime join_date;

    public MemberResponseDto(Member_table member_table) {
        this.memberid = member_table.getMemberid();
        this.memberLoginid = member_table.getMemberLoginid();
        this.memberName = member_table.getMemberName();
        this.memberPassword = member_table.getMemberPassword();
        this.memberRank = member_table.getMemberRank();
        this.memberPerchase = member_table.getMemberPerchase();
        this.memberPhone = member_table.getMemberPhone();
        this.join_date = member_table.getJoin_date();
    }

}
