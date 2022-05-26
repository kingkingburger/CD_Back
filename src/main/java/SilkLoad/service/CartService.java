package SilkLoad.service;

import SilkLoad.dto.OrderFormDto;
import SilkLoad.dto.ProductFormDto;
import SilkLoad.dto.ProductRecordDto;
import SilkLoad.entity.Cart;
import SilkLoad.entity.Members;
import SilkLoad.entity.OrderEnum.OrderType;
import SilkLoad.entity.Orders;
import SilkLoad.entity.Product;
import SilkLoad.repository.CartRepository;
import SilkLoad.repository.MemberRepository;
import SilkLoad.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final MemberService memberService;
    private final ProductService productService;


    /**
     * 판매자 기준으로 물건 가져오기
     * @param id
     * @return
     */
    @Transactional
    public List<ProductRecordDto> getSellerProduct(String id) {
        Members members = memberRepository.findByLoginId(id).get();

        List<Cart> byMemberid = cartRepository.findByMember(members);

        List<Product> productList = new ArrayList<>();

        for (Cart cart : byMemberid) {
            Product product = cart.getProduct();
            productList.add(product);
        }

        List<ProductRecordDto> productRecordDtoList = productService.getProductRecordDtoList(productList);

        return productRecordDtoList;
    }

    @Transactional
    public void deleteProductInCart(Long id){
        cartRepository.deleteByProductId(id);
    }

    /**
     * Cart에 담기 service
     * @param orderFormDto
     * @return
     */
    @Transactional
    public boolean save(OrderFormDto orderFormDto) {

        Long memberId = orderFormDto.getMemberId();
        Long productId = orderFormDto.getProductId();

        Optional<Members> byMemberId = memberRepository.findById(memberId);
        Optional<Product> byProductId = productRepository.findById(productId);

        if(byMemberId.isPresent() && byProductId.isPresent() ) {

            Members member = byMemberId.get();
            Product product = byProductId.get();
            Cart cart = crateCart(member, product);
            cartRepository.save(cart);
            return true;
        }
        return false;
    }


    private Cart crateCart(Members member_id, Product product_id) {
        return Cart.builder()
                .memberid(member_id)
                .productid(product_id)
                .build();
    }
}
