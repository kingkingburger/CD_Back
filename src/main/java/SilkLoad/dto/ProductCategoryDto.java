package SilkLoad.dto;

import SilkLoad.entity.ProductEnum.ProductTime;
import SilkLoad.entity.ProductEnum.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ProductCategoryDto {
    private Long id;
    private String name;
    private Long auctionPrice;
    private Long instantPrice;
    private String explanation;
    private LocalDateTime createdDate;
    private ProductTime productTime;
    private ProductType productType;
    private String first;
    private String second;
    private String uploadFileName;  //회원이 올린 파일 이름
    private String storeFileName;   //실제 저장 될 파일 이름

    public ProductCategoryDto(Long id,
                              String name,
                              Long auctionPrice,
                              Long instantPrice,
                              String explanation,
                              LocalDateTime createdDate,
                              ProductTime productTime,
                              ProductType productType,
                              String first,
                              String second,
                              String uploadFileName,
                              String storeFileName) {
        this.id = id;
        this.name = name;
        this.auctionPrice = auctionPrice;
        this.instantPrice = instantPrice;
        this.explanation = explanation;
        this.createdDate = createdDate;
        this.productTime = productTime;
        this.productType = productType;
        this.first = first;
        this.second = second;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
