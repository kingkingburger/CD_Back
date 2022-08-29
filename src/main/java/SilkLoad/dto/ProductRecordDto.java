package SilkLoad.dto;

import SilkLoad.entity.*;
import SilkLoad.entity.ProductEnum.ProductTime;
import SilkLoad.entity.ProductEnum.ProductType;
import lombok.Builder;
import lombok.Data;
import org.apache.tomcat.jni.Local;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ProductRecordDto {

    @NotNull
    private Long id;
    @NotBlank
    private String name;
    private Long auctionPrice; //경매가격
    @NotNull
    private Long instantPrice; //즉시거래가격
    private String explanation; //물품상세설명
    private String predictionImage;
    @NotNull
    private LocalDateTime deadLine;
    @NotNull
    private ProductTime productTime;
    @NotNull
    private ProductType productType;
    @NotNull
    private CategoryRecordDto categoryRecordDto;
    @NotNull
    private List<ProductImageRecordDto> productImagesList;

    public ProductRecordDto(Long id,
                            String name,
                            Long auctionPrice,
                            Long instantPrice,
                            String explanation,
                            String predictionImage,
                            LocalDateTime deadLine,
                            ProductTime productTime,
                            ProductType productType,
                            CategoryRecordDto categoryRecordDto,
                            List<ProductImageRecordDto> productImagesList
                            ) {
        this.id = id;
        this.name = name;
        this.auctionPrice = auctionPrice;
        this.instantPrice = instantPrice;
        this.explanation = explanation;
        this.predictionImage = predictionImage;
        this.deadLine = deadLine;
        this.productTime = productTime;
        this.productType = productType;
        this.categoryRecordDto = categoryRecordDto;
        this.productImagesList = productImagesList;
    }
}
