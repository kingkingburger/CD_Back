package SilkLoad.example.service;


import SilkLoad.dto.ProductFormDto;
import SilkLoad.entity.Members;
import SilkLoad.entity.Product;
import SilkLoad.repository.MemberRepository;
import SilkLoad.repository.ProductImageRepository;
import SilkLoad.repository.ProductRepository;
import SilkLoad.service.MemberService;
import SilkLoad.service.ProductService;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

@SpringBootTest
public class ProductAddTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ProductImageRepository productImageRepository;

    @Test
    void 물품_DB에많이넣기_테스트() throws IOException {
        Product product = productService.findById_Product((long) 314);


        //더미 이미지 만들기, jpg -> MultipartFile로 만들기가 힘들다
        List<MultipartFile> imageFiles = List.of(
                new MockMultipartFile("7a88ca37-9e3b-4be3-af72-1bd6e5b7fe9f.jpg",
                        "7a88ca37-9e3b-4be3-af72-1bd6e5b7fe9f.jpg",
                        MediaType.IMAGE_JPEG_VALUE,
                        "test1".getBytes())
        );
        
        //새로운 물품 만들기
        ProductFormDto test_product = ProductFormDto.builder()
                .name(product.getName())
                .instancePrice(product.getInstantPrice())
                .auctionPrice(product.getAuctionPrice())
                .Explanation(product.getExplanation())
                .category("패딩점퍼,여성의류")
                .imageFileList(imageFiles)
                .build();

        //DB에 있는 memeber 가지고 오기, member가 정해지지 않으면 영속성에서 외래키 오류가 발생한다
        Members members = memberRepository.findById((long) 6).get();

        productService.save(test_product, members);

        productImageRepository.save(product.getProductImagesList().get(0));

    }
}
