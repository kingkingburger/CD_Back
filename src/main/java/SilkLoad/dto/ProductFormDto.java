package SilkLoad.dto;


import SilkLoad.entity.ProductTime;
import SilkLoad.entity.ProductType;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
public class ProductFormDto {

    @NotBlank
    private String name; //물품이름

    private Long auctionPrice; //경매가격
    @NotNull
    private Long instancePrice; //즉시거래가격
    @NotBlank
    private String category; // 카테고리
    private List<MultipartFile> imageFileList = new ArrayList<MultipartFile>(); //이미지
    private String Explanation; //물품상세설명
    private LocalDateTime createdDate = LocalDateTime.now(); //생성일자

    @NotNull
    private ProductTime productTime;


}
