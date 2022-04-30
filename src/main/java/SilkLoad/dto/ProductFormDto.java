package SilkLoad.dto;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data

public class ProductFormDto {

    private Long Id; // PK
    @NotBlank
    private String name; //물품이름
    @NotBlank
    private Long auctionPrice; //경매가격
    @NotBlank
    private Long instancePrice; //즉시거래가격
    @NotBlank
    private String category; // 카테고리
    private String imageNameSrc; //이미지
    private String Explanation; //물품상세설명
    private LocalDateTime createdDate = LocalDateTime.now() ; //생성일자


}
