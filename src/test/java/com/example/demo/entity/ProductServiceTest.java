package com.example.demo.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    void save() {

        Member_table m = new Member_table();
        m.setMemberLoginid("1135");
        m.setMemberName("dnjsalsgh");
        m.setMemberPassword("1234");
        m.setMemberPhone("12351626");

        memberRepository.save(m);

        Product params = Product.builder()
                .productName("아이폰팔아요")
                .productPrice((long)50000)
                .productExplanation("01035862056")
                .productCategory("전자제품")
                .build();
        params.setMember_table(m);
        System.out.println("params = " + params);
        productRepository.save(params);
    }
}
