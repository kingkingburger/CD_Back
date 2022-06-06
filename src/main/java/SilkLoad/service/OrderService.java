package SilkLoad.service;

import SilkLoad.dto.OrderBuyAuctionDto;
import SilkLoad.dto.OrderBuyNowDto;
import SilkLoad.dto.OrderHistoryDto;
import SilkLoad.entity.ChatRoom;
import SilkLoad.entity.Members;
import SilkLoad.entity.OrderEnum.OrderType;
import SilkLoad.entity.Orders;
import SilkLoad.entity.Product;
import SilkLoad.entity.ProductEnum.ProductType;
import SilkLoad.repository.ChatRoomRepository;
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
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public Orders saveBuyNow(OrderBuyNowDto orderBuyNowDto) {


        Long memberId = orderBuyNowDto.getMemberId();
        Long productId = orderBuyNowDto.getProductId();

        Members member = memberRepository.findById(memberId).get();
        Product product = productRepository.findById(productId).get();

        if( (member != null && product != null) && (product.getMembers().getId() != member.getId()) ) {

            Orders order = createBuyNowOrder(member, product);
            List<Orders> SameTimeOrderList = getSameTimeOrders(order);
            log.info("겹치는 시간 여부 ={}, {}", order.getProduct().getId(), order.getOrderDateTime());

            List<Orders> byProduct_idANDOrderTypeNot = orderRepository.findByProduct_IdAndOrderTypeNot (order.getProduct().getId(), OrderType.unRegistered);

            if (SameTimeOrderList.isEmpty() && byProduct_idANDOrderTypeNot.isEmpty() ) {

                product.setProductType(ProductType.trading);
                Product saveProduct = productRepository.save(product);
                Orders saveOrder = orderRepository.save(order);

                if (saveOrder != null && saveProduct != null) {
                    ChatRoom chatRoom = createChatRoom(member, saveProduct);
                    if (chatRoom != null)
                        return saveOrder;
                }
            }

        }
        return null;

    }

    @Transactional
    public Orders saveBuyAuctionDto(OrderBuyAuctionDto orderBuyAuctionDto) {


        Long memberId = orderBuyAuctionDto.getMemberId();
        Long productId = orderBuyAuctionDto.getProductId();

        Members member = memberRepository.findById(memberId).get();
        Product product = productRepository.findById(productId).get();

        if( (member != null && product != null) && (product.getMembers().getId() != member.getId()) ) {

            Orders order = createBuyAuctionOrder(member, product, orderBuyAuctionDto.getAuctionPrice());
            List<Orders> SameTimeOrderList = getSameTimeOrders(order);
            log.info("겹치는 시간 여부 ={}, {}", order.getProduct().getId(), order.getOrderDateTime());

            List<Orders> byProduct_idANDOrderTypeNot = orderRepository.findByProduct_IdAndOrderTypeNot (order.getProduct().getId(), OrderType.unRegistered);


            Long maxAuctionPrice = orderRepository.findByProductIdMaxAuctionPrice(order.getProduct().getId());


            if (SameTimeOrderList.isEmpty() && byProduct_idANDOrderTypeNot.isEmpty() && order.getOfferPrice() > maxAuctionPrice ) {
                return orderRepository.save(order);
            }

        }
        return null;




    }

    private List<Orders> getSameTimeOrders(Orders order) {
        LocalDateTime orderDateTime = order.getOrderDateTime();
        LocalDateTime generateDateTime = LocalDateTime.of(orderDateTime.getYear(), orderDateTime.getMonthValue(), orderDateTime.getDayOfMonth(),
                orderDateTime.getHour(), orderDateTime.getMinute(), orderDateTime.getSecond());


        List<Orders> SameTimeOrderList = orderRepository.findByProduct_IdAndOrderDateTime(
                order.getProduct().getId(),
                generateDateTime
        );
        return SameTimeOrderList;
    }

    @Transactional
    public List<OrderHistoryDto> findMemberSaleOrder(Long memberId, Pageable pageable) {

        return orderRepository.findMemberSaleOrder(memberId, pageable).getContent();

    }

    @Transactional
    public List<OrderHistoryDto> findMemberPurchaseOrder (Long memberId, Pageable pageable) {

        return orderRepository.findMemberPurchaseOrder(memberId, pageable).getContent();

    }


    @Transactional
    public Orders completeOrder(Long id) {

        Optional<Orders> optionalOrders = orderRepository.findById(id);
        if(optionalOrders.isPresent()) {

            Orders order = optionalOrders.get();

            if( order.getOrderType() == OrderType.trading
            && order.getProduct().getProductType() == ProductType.trading) {

                order.setOrderType(OrderType.complete);
                order.getProduct().setProductType(ProductType.soldOut);
                return orderRepository.save(order);
            }
        }
        return null;
    }

    @Transactional
    public Orders tradingOrder(Long id) {

        Optional<Orders> optionalOrders = orderRepository.findById(id);
        if(optionalOrders.isPresent()) {

            Orders order = optionalOrders.get();

            if( order.getOrderType() == OrderType.waiting
                    &&  order.getProduct().getProductType() == ProductType.sale) {

                order.setOrderType(OrderType.trading);
                order.getProduct().setProductType(ProductType.trading );
                ChatRoom chatRoom = createChatRoom(order.getMemberBuyer(), order.getProduct());
                if (chatRoom != null)
                    return orderRepository.save(order);
            }
        }
        return null;
    }

    @Transactional
    public Orders cancelOrder(Long id) {

        Optional<Orders> optionalOrders = orderRepository.findById(id);
        if(optionalOrders.isPresent()) {

            Orders order = optionalOrders.get();

            if( (order.getOrderType() != OrderType.complete && order.getOrderType() != OrderType.cancel)
                    &&  (order.getProduct().getProductType() != ProductType.cancel || order.getProduct().getProductType() != ProductType.soldOut) ) {

                order.setOrderType(OrderType.cancel );
                order.getProduct().setProductType(ProductType.cancel );
                return orderRepository.save(order);
            }
        }
        return null;
    }


    @Transactional
    public Long findByMaxAuctionPrice( Long productId) {

        return orderRepository.findByProductIdMaxAuctionPrice(productId);

    }


    //즉시 거래일 때의 createOrder
   private Orders createBuyAuctionOrder(Members member, Product product, Long offerPrice) {

       Orders order = Orders.builder()
               .memberBuyer(member)
               .product(product)
               .offerPrice(offerPrice)
               .orderType(OrderType.waiting)
               .orderDateTime(LocalDateTime.now())
               .build();
       return  order;

   }

    private Orders createBuyNowOrder(Members member, Product product) {

        Orders order = Orders.builder()
                .memberBuyer(member)
                .product(product)
                .offerPrice(product.getInstantPrice())
                .orderType(OrderType.trading)
                .orderDateTime(LocalDateTime.now())
                .build();

        return  order;

    }

    private ChatRoom createChatRoom(Members members, Product product) {

        ChatRoom chatRoom = ChatRoom.builder()
                .name(product.getMembers().getName() + "님의 " +  product.getName() + " 방")
                .membersBuyer(members)
                .product(product)
                .createDateTime(LocalDateTime.now())
                .build();

        return  chatRoomRepository.save(chatRoom);

    }

}
