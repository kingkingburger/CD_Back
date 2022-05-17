package SilkLoad.entity;

import SilkLoad.entity.OrderEnum.OrderType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Members memberBuyer;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    private Long offerPrice;

    private OrderType orderType;

    @NotNull
    private LocalDateTime orderDate;

}
