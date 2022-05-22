package SilkLoad.repository;

import SilkLoad.dto.CartDto;
import SilkLoad.entity.Cart;
import SilkLoad.entity.Members;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CartRepositoryTest {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ProductRepository productRepository;

    @Test
    void 카트에물품_넣기_테스트(){

        Cart build = Cart.builder()
                .memberid(memberRepository.getById((long) 1))
                .productid(productRepository.findById((long) 1).get())
                .build();

        cartRepository.save(build);

    }
}
