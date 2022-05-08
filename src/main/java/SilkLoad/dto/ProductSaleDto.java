package SilkLoad.dto;

import SilkLoad.entity.*;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ProductSaleDto {

    @NotNull
    private Long id;
    @NotBlank
    private String name;
    private Long auctionPrice; //경매가격
    @NotBlank
    private Long instantPrice; //즉시거래가격
    private String explanation; //물품상세설명
    @NotBlank
    private String deadLine;
    @NotNull
    private ProductTime productTime;
    @NotNull
    private ProductType productType;
    @NotNull
    private Category category;
    @NotNull
    private List<ProductImage> productImagesList;


}
