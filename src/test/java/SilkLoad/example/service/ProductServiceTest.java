package SilkLoad.example.service;

import SilkLoad.dto.MemberFormDto;
import SilkLoad.dto.ProductFormDto;
import SilkLoad.dto.ProductRecordDto;
import SilkLoad.entity.Members;
import SilkLoad.entity.Product;
import SilkLoad.entity.ProductEnum.ProductTime;
import SilkLoad.entity.ProductEnum.ProductType;
import SilkLoad.service.MemberService;
import SilkLoad.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.support.MultipartFilter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Autowired
    ProductService productService;
    @Autowired
    MemberService memberService;

    @Test
    void 물품_save_테스트(){
        Product product = Product.builder()
                .name("테스트 입니다.")
                .instantPrice((long)10000)
                .auctionPrice((long)20000)
                .explanation("productState가 재대로 들어가는지")
                .createdDate(LocalDateTime.now())
                .productType(ProductType.sale)
                .build();

        System.out.println("product = " + product);
    }

    @Test
    void 물품_가져오기_테스트(){
        List<ProductRecordDto> allProduct = productService.findAllProduct();
        ProductRecordDto byId_product = productService.findById_ProductRecordDto(5L);
//        System.out.println("byId_product = " + byId_product);
        for (ProductRecordDto product : allProduct) {
            System.out.println("product = " + product.getProductImagesList().get(0).getStoreFileName());
        }
    }

    @Test
    void productLazeTest() throws IOException {

        ProductFormDto productFormDto = new ProductFormDto();
        productFormDto.getImageFileList().add(new MockMultipartFile("test1", "test1.PNG", MediaType.IMAGE_PNG_VALUE, "test1".getBytes(StandardCharsets.UTF_8)));


        productFormDto.setCategory("옷,바지");
        productFormDto.setName("사과");
        productFormDto.setAuctionPrice(1L);
        productFormDto.setInstancePrice(2L);
        productFormDto.setExplanation("테스트 중");
        productFormDto.setProductTime(ProductTime.ONE_DAY);

        MemberFormDto mDto = MemberFormDto.builder().loginId("강준호")
                .name("준호")
                .password("1234")
                .build();

        memberService.save(mDto);
        Members member = memberService.findByLoginId("강준호");
        productService.save(productFormDto, member);

//        List<ProductRecordDto> allProduct = productService.findAllProduct();
//        Product byId_product = productService.findById_Product(1L);

        ProductRecordDto productRecordDto = productService.findById_ProductRecordDto(1L);
        System.out.println(productRecordDto.getProductImagesList());
//        System.out.println(byId_product.getProductImagesList());
//        System.out.println( allProduct.get(0).getProductImagesList());

    }
}
