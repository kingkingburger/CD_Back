package com.example.demo.entity;

import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.ProductRepository;
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
        m.setMemberLoginid("1137");
        m.setMemberName("dnjsalsgh");
        m.setMemberPassword("1234");
        m.setMemberPhone("12351626");

        memberRepository.save(m);

        Product params = new Product();

        params.setProductName("갤럭시팔아요");
        params.setProductExplanation("235235");
        params.setProductPrice((long)12345125);
        params.setProductCategory("핸드폰");
        params.setMember_table(m);

        System.out.println("params = " + params);
        productRepository.save(params);
    }

    @Test // 내부조인으로 검색
    void testManyToOneSelect(){
        Product product = productRepository.findById(8L).get();
        System.out.println("product.getProductName() = " + product.getProductName());
        System.out.println("product.getProductCategory() = " + product.getProductCategory());
        System.out.println("product.getMember_table() = " + product.getMember_table());
    }

    @Test // 양방향 연관관계
    void testTwoWayMapping(){
        Member_table member_table = memberRepository.findById(89L).get();
        System.out.println("member_table = " + member_table);

        List<Product> productList = member_table.getProductList();
        System.out.println("productList = " + productList);
        for(Product p: productList){
            System.out.println("p = " + p.toString());
        }
    }
}
