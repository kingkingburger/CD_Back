package SilkLoad.service;

import SilkLoad.dto.OrderFormDto;
import SilkLoad.dto.ProductRecordDto;
import SilkLoad.entity.Members;
import SilkLoad.entity.OrderEnum.OrderType;
import SilkLoad.entity.Orders;
import SilkLoad.entity.Product;
import SilkLoad.repository.MemberRepository;
import SilkLoad.repository.OrderRepository;
import SilkLoad.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    private final MemberService memberService;
    private final ProductService productService;

    @Transactional
    public List<ProductRecordDto> findByIdProductDtoList(String id) {

        List<Product> byLoginIdProductList = memberService.findByLoginIdProductList(id);
        return productService.getProductRecordDtoList(byLoginIdProductList);

    }


    @Transactional
    public boolean save(OrderFormDto orderFormDto) {

        Long memberId = orderFormDto.getMemberId();
        Long productId = orderFormDto.getProductId();

        Optional<Members> byMemberId = memberRepository.findById(memberId);
        Optional<Product> byProductId = productRepository.findById(productId);

        if(byMemberId.isPresent() && byProductId.isPresent() ) {

            Members member = byMemberId.get();
            Product product = byProductId.get();
            if( product.getId() == member.getId()){
                return false;
            }
            Orders order = crateOrder(member, product);

            orderRepository.save(order);

            return true;
        }
        return false;

    }

    //즉시 거래일 때의 createOrder
   private Orders crateOrder(Members member, Product product) {
        return Orders.builder()
                .memberBuyer(member)
                .product(product)
                .offerPrice(product.getInstantPrice())
                .orderType(OrderType.trading)
                .orderDate(LocalDateTime.now())
                .build();

    }

}
