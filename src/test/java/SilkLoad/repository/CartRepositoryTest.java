package SilkLoad.repository;

import SilkLoad.dto.CartDto;
import SilkLoad.dto.ProductRecordDto;
import SilkLoad.entity.Cart;
import SilkLoad.entity.Members;
import SilkLoad.entity.Product;
import SilkLoad.service.CartService;
import SilkLoad.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CartRepositoryTest {
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CartService cartService;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderService orderService;
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


    @Test
    void 카트에서memberid별로_물품꺼내기_테스트(){
        PageRequest pageRequest = PageRequest.of(1,8);
        PageRequest next = pageRequest.next();
        pageRequest.getPageSize();

        Members members = memberRepository.findById((long) 1).get();
        List<Cart> content = cartRepository.findByMember(members, next).getContent();
        System.out.println(content.size());
        for (Cart cart : content) {
            System.out.println("cart = " + cart.toString());
        }
    }

    @Test
    void 카트에서_물품꺼내기_테스트(){
        PageRequest pageRequest = PageRequest.of(1,8);
        PageRequest next = pageRequest.next();
        List<ProductRecordDto> sellerProduct = cartService.getSellerProduct("1",next).getContent();
        System.out.println("sellerProduct = " + sellerProduct);
    }

    @Test
    void 카트에서_물품제거_테스트(){
        cartRepository.deleteByProductId((long)98);
    }
}
