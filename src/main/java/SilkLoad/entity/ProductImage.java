package SilkLoad.entity;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@ToString(exclude = "product")
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductImage_ID")
    private Long id; // PK

    @ManyToOne
    @JoinColumn(name="PRODUCT_ID", nullable=false)
    private Product product;

    private String uploadFileName;  //회원이 올린 파일 이름
    private String storeFileName;   //실제 저장 될 파일 이름

    @Builder
    public ProductImage(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    /**
     * ProductImg <=> Product 양방향 연관관계 객체
     * @param param_product
     */
    public void changeProduct(Product param_product) {
        this.product = param_product;
        param_product.getProductImagesList().add(this);
    }

}
