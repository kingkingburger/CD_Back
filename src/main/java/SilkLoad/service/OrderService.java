package SilkLoad.service;

import SilkLoad.dto.OrderFormDto;
import SilkLoad.dto.TradeOrderDto;
import SilkLoad.entity.Members;
import SilkLoad.entity.OrderEnum.OrderType;
import SilkLoad.entity.Orders;
import SilkLoad.entity.Product;
import SilkLoad.entity.ProductEnum.ProductType;
import SilkLoad.repository.MemberRepository;
import SilkLoad.repository.OrderRepository;
import SilkLoad.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;


    @Transactional
    public Orders saveFormDto(OrderFormDto orderFormDto) {


        Long memberId = orderFormDto.getMemberId();
        Long productId = orderFormDto.getProductId();

        Members member = memberRepository.findById(memberId).get();
        Product product = productRepository.findById(productId).get();

        if( (member != null && product != null) && (product.getId() != member.getId()) ) {


            Orders order = createOrder(member, product);
            LocalDateTime orderDateTime = order.getOrderDateTime();
            LocalDateTime generateDateTime = LocalDateTime.of(orderDateTime.getYear(), orderDateTime.getMonthValue(), orderDateTime.getDayOfMonth(),
                    orderDateTime.getHour(), orderDateTime.getMinute(), orderDateTime.getSecond());
            order.getOrderDateTime().getYear();
            List<Orders> byProduct_idAndOrderDateTime = orderRepository.findByProduct_IdAndOrderDateTime(
                                                                            order.getProduct().getId(),
                                                                            generateDateTime
                                                                        );
            log.info("겹치는 시간 여부 ={}, {}", order.getProduct().getId(), order.getOrderDateTime());

            List<Orders> byProduct_idANDOrderTypeNot = orderRepository.findByProduct_IdAndOrderTypeNot (order.getProduct().getId(), OrderType.unRegistered);

            if (byProduct_idAndOrderDateTime.isEmpty() && byProduct_idANDOrderTypeNot.isEmpty() ) {
                return orderRepository.save(order);
            }

        }
        return null;

    }
    @Transactional
    public List<TradeOrderDto> findMemberSaleOrder(Long memberId, Pageable pageable) {

        return orderRepository.findMemberSaleOrder(memberId, pageable).getContent();

    }

    @Transactional
    public List<TradeOrderDto> findMemberPurchaseOrder (Long memberId, Pageable pageable) {

        return orderRepository.findMemberPurchaseOrder(memberId, pageable).getContent();

    }


    @Transactional
    public Orders completeOrder(Long id) {

        Optional<Orders> optionalOrders = orderRepository.findById(id);
        if(optionalOrders.isPresent()) {

            Orders order = optionalOrders.get();

            if( order.getOrderType() == OrderType.trading) {

                order.setOrderType(OrderType.complete);
                order.getProduct().setProductType(ProductType.soldOut);
                return orderRepository.save(order);
            }
        }
        return null;
    }


    //즉시 거래일 때의 createOrder
   private Orders createOrder(Members member, Product product) {
        return Orders.builder()
                .memberBuyer(member)
                .product(product)
                .offerPrice(product.getInstantPrice())
                .orderType(OrderType.trading)
                .orderDateTime(LocalDateTime.now())
                .build();

    }

}
