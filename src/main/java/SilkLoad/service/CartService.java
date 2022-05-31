package SilkLoad.service;


import SilkLoad.dto.CartDto;
import SilkLoad.dto.ProductRecordDto;
import SilkLoad.entity.Cart;
import SilkLoad.entity.Members;
import SilkLoad.entity.Product;
import SilkLoad.repository.CartRepository;
import SilkLoad.repository.MemberRepository;
import SilkLoad.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Page<ProductRecordDto> getSellerProduct(String id, Pageable pageable) {
        Members members = memberRepository.findByLoginId(id).get();

        Page<Cart> byMemberid = cartRepository.findByMember(members, pageable);

        List<Product> productList = new ArrayList<>();

        for (Cart cart : byMemberid) {
            Product product = cart.getProduct();
            productList.add(product);
        }

        //List 를 Page로 변환
        List<ProductRecordDto> productRecordDtoList = productService.getProductRecordDtoList(productList);
        Page<ProductRecordDto> productRecordDtos = new PageImpl<>(productRecordDtoList, pageable, productList.size());
        
        return productRecordDtos;
    }

    /**
     * id로 Cart에 있는 물품 제거 
     * @param id
     */
    @Transactional
    public void deleteProductInCart(Long id){
        cartRepository.deleteByProductId(id);
    }

    /**
     * Cart에 담기 service
     * @param cartDto
     * @return
     */
    @Transactional
    public boolean save(CartDto cartDto) {

        Long memberId = cartDto.getMemberid();
        Long productId = cartDto.getProductid();

        Optional<Members> byMemberId = memberRepository.findById(memberId);
        Optional<Product> byProductId = productRepository.findById(productId);

        if(byMemberId.isPresent() && byProductId.isPresent() ) {

            Members member = byMemberId.get();
            Product product = byProductId.get();
            Cart cart = crateCart(member, product);

            //카트안에 물품이 존재 하는지
            if(!cartRepository.existsByProductId(product.getId())){
                cartRepository.save(cart);
            }
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
