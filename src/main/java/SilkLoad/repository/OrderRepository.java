package SilkLoad.repository;

import SilkLoad.dto.OrderHistoryDto;
import SilkLoad.entity.OrderEnum.OrderType;
import SilkLoad.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long>  {

/*
    @Query("SELECT new SilkLoad.dto.SaleOrderDto(p.id, p.name, p.auctionPrice, p.instantPrice, p.explanation, p.productType, p.productTime, p.createdDate, o.offerPrice, o.memberBuyer.name, o.orderDateTime )  FROM Product p join Orders o on p.id = o.product.id where p.members.id = :memberId")
    Page<SaleOrderDto> findMemberProduct(@Param("memberId") Long memberId, Pageable pageable);

*/

    List<Orders> findByProduct_IdAndOrderDateTime(Long productId, LocalDateTime localDateTime);


    List<Orders> findByProduct_IdAndOrderTypeNot (Long productId, OrderType orderType);

    @Query("SELECT MAX(o.offerPrice) FROM Orders o GROUP BY o.product.id HAVING o.product.id = :productId")
    Long findByProductIdMaxAuctionPrice(@Param("productId") Long productId);


    /**
     * @param memberId product 테이블에서 memberId에 맞는 row 추출
     * @return product id에 맞는 최신(orderDateTime) order
     */
    @Query("SELECT " +
            "NEW SilkLoad.dto.OrderHistoryDto(p.id, p.name, p.auctionPrice, p.instantPrice, p.productType, p.productTime, p.createdDate," +
            " original.id, original.orderType, original.offerPrice, original.memberBuyer.name, original.orderDateTime) " +
            "FROM Orders original " +
            "LEFT JOIN Orders copy " +
            "ON original.product.id = copy.product.id " +
            "AND original.orderDateTime < copy.orderDateTime " +
            "JOIN Product p ON p.id = original.product.id " +
            "WHERE copy.product.id IS NULL AND p.members.id = :memberId ORDER BY original.product.id"
    )
    Page<OrderHistoryDto> findMemberSaleOrder(@Param("memberId") Long memberId, Pageable pageable);


    @Query("SELECT " +
            "NEW SilkLoad.dto.OrderHistoryDto(p.id, p.name, p.auctionPrice, p.instantPrice, p.productType, p.productTime, p.createdDate," +
            " o.id, o.orderType, o.offerPrice, o.memberBuyer.name, o.orderDateTime) " +
            "FROM Orders o " +
            "join Product p " +
            "on o.product.id = p.id " +
            "WHERE o.orderType <> SilkLoad.entity.OrderEnum.OrderType.unRegistered " +
            "AND o.orderType <> SilkLoad.entity.OrderEnum.OrderType.waiting " +
            "AND o.memberBuyer.id = :memberId"
    )
    Page<OrderHistoryDto> findMemberPurchaseOrder(@Param("memberId") Long memberId, Pageable pageable);



}
