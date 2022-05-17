package SilkLoad.entity;


import SilkLoad.entity.ProductEnum.ProductTime;
import SilkLoad.entity.ProductEnum.ProductType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)

@Entity
@Data
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRODUCT_ID")
    private Long id; // PK

    private String name; //물품이름
    private Long auctionPrice; //경매가격
    private Long instantPrice; //즉시거래가격
    private String explanation; //물품상세설명

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    private ProductTime productTime;

    private LocalDateTime createdDate = LocalDateTime.now(); //생성일자

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", nullable = false) //Product 테이블에 있는 것을 매핑 , nullable을 false로 해서 내부 join으로 변경
    private Members members;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;

    @ToString.Exclude
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<ProductImage> productImagesList = new ArrayList<ProductImage>();

    /**
     * Product <=> Category 양방향 연관관계
     * @param category
     */
    //양방향 연관관계 객체간에 관계 유지, toString 무한 호출 조심
    public void changeCategory(Category category) {
        this.category = category;
        category.getProductList().add(this);
    }

    @Builder
    public Product(Long id,
                   String name,
                   Long auctionPrice,
                   Long instantPrice,
                   String explanation,
                   LocalDateTime createdDate,
                   Members members,
                   Category category,
                   ProductTime productTime,
                   ProductType productType) {

        this.id = id;
        this.name = name;
        this.auctionPrice = auctionPrice;
        this.instantPrice = instantPrice;
        this.explanation = explanation;
        this.createdDate = createdDate;
        this.productType = productType;
        this.members = members;
        this.category = category;
        this.productTime = productTime;
        this.productImagesList = new ArrayList<>();
    }

}
