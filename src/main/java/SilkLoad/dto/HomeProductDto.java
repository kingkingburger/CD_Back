package SilkLoad.dto;

import SilkLoad.entity.Category;
import SilkLoad.entity.ProductEnum.ProductTime;
import SilkLoad.entity.ProductEnum.ProductType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class HomeProductDto {

    @NotNull
    private Long id;
    @NotBlank
    private String name;

    private Long auctionPrice; //경매가격

    @NotNull
    private Long instantPrice; //즉시거래가격
    private String explanation; //물품상세설명
    private String predictionImage;
    private LocalDateTime deadLine;

    private LocalDateTime createdDateTime;

    @NotNull
    private ProductTime productTime;
    @NotNull
    private ProductType productType;

    @NotNull
    private String categoryFirst;

    private String categorySecond;

    private String categoryThird;
    @NotNull
    private List<ProductImageRecordDto> productImagesList;

    private String uploadFileName;  //회원이 올린 파일 이름
    private String storeFileName;   //실제 저장 될 파일 이름

    private Long offerPrice;

    public HomeProductDto(Long id,
                          String name,
                          Long auctionPrice,
                          Long instantPrice,
                          String explanation,
                          String predictionImage,
                          String categoryFirst,
                          String categorySecond,
                          String categoryThird,
                          LocalDateTime createdDateTime,
                          ProductTime productTime,
                          ProductType productType,
                          String uploadFileName,
                          String storeFileName,
                          Long offerPrice) {
        this.id = id;
        this.name = name;
        this.auctionPrice = auctionPrice;
        this.instantPrice = instantPrice;
        this.explanation = explanation;
        this.predictionImage = predictionImage;
        this.categoryFirst = categoryFirst;
        this.categorySecond = categorySecond;
        this.categoryThird = categoryThird;
        this.deadLine = productDeadLine(createdDateTime, productTime);
        this.createdDateTime = createdDateTime;
        this.productTime = productTime;
        this.productType = productType;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.offerPrice = offerPrice;

    }

    public LocalDateTime productDeadLine(LocalDateTime createdProduct, ProductTime productTime) {
        if (productTime == ProductTime.ONE_DAY) {
            createdProduct = createdProduct.plusDays(1);
        } else if (productTime == ProductTime.TWO_DAY) {
            createdProduct = createdProduct.plusDays(2);
        }

        return createdProduct;
    }




}
