package com.example.demo.dto;


import com.example.demo.entity.Member_table;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
@Data
//@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class MemberRequestDto {

    @NotEmpty
    private String memberLoginid;

    private String memberName;
    @NotEmpty
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
