package com.example.demo.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Members {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    private Integer ranks;

    private Integer numberPurchase;

    private LocalDate createDate = LocalDate.now();

    @Builder
    public Members(Long id, String loginId, String name, String password, Integer ranks, Integer numberPurchase) {
        this.id = id;
        this.loginId = loginId;
        this.name = name;
        this.password = password;
        this.ranks = ranks;
        this.numberPurchase = numberPurchase;
        this.createDate = LocalDate.now();
    }
}