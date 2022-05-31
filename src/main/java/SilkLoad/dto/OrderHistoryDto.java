package SilkLoad.dto;

import SilkLoad.entity.OrderEnum.OrderType;
import SilkLoad.entity.ProductEnum.ProductTime;
import SilkLoad.entity.ProductEnum.ProductType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderHistoryDto {

    private Long productId;
    private String productName;
    private Long auctionPrice; //경매가격
    private Long instantPrice; //즉시거래가격
    private ProductType productType;
    private ProductTime productTime;
    private LocalDateTime deadLine;
    private LocalDateTime productDateTime;

    private Long orderId;
    private OrderType orderType;
    private Long offerPrice;
    private String buyerName;
    private LocalDateTime orderDateTime;

    public OrderHistoryDto(Long productId, String productName, Long auctionPrice, Long instantPrice, ProductType productType, ProductTime productTime, LocalDateTime productDateTime, Long orderId, OrderType orderType, Long offerPrice, String buyerName, LocalDateTime orderDateTime) {

        this.productId = productId;
        this.productName = productName;
        this.auctionPrice = auctionPrice;
        this.instantPrice = instantPrice;
        this.productType = productType;
        this.productTime = productTime;
        this.productDateTime = productDateTime;
        this.orderId = orderId;
        this.orderType = orderType;
        this.offerPrice = offerPrice;
        this.buyerName = buyerName;
        this.orderDateTime = orderDateTime;
        this.deadLine = null;

    }



}
