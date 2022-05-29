package SilkLoad.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Data
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BASKET_ID")
    private Long id; // PK


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Members member;  //구매자의 id

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product; //물품의 id

    private LocalDate createDate = LocalDate.now();

    @Builder
    public Cart(Members memberid,
                Product productid) {
        this.member = memberid;
        this.product = productid;
    }
}
