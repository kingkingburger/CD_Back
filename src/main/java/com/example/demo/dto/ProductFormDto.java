package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    private List<MultipartFile> imageFileList = new ArrayList<MultipartFile>(); //이미지
    private String Explanation; //물품상세설명
    private LocalDateTime createdDate = LocalDateTime.now() ; //생성일자

}
