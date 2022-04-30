package SilkLoad.entity;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductImage_ID")
    private Long id; // PK

    @ManyToOne
    @JoinColumn(name="PRODUCT_ID", nullable=false)
    private Product product;

    /**
     * 회원이 올린 파일 이름
     */
    private String uploadFileName;
    /**
     * 실제 저장 될 파일 이름
     */
    private String storeFileName;

    @Builder
    public ProductImage(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

    /**
     * @param product, 양방향 연관관계 객체
     */
    public void changeProduct(Product product ) {
        this.product = product;
        product.getProductImagesList().add(this);
    }

}
