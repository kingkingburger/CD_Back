package SilkLoad.example.service;

import SilkLoad.dto.*;
import SilkLoad.entity.Members;
import SilkLoad.entity.Product;
import SilkLoad.entity.ProductEnum.ProductTime;
import SilkLoad.entity.ProductEnum.ProductType;
import SilkLoad.repository.MemberRepository;
import SilkLoad.repository.OrderRepository;
import SilkLoad.repository.ProductRepository;
import SilkLoad.service.MemberService;
import SilkLoad.service.OrderService;
import SilkLoad.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Slf4j
public class ProductServiceTest {

    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository repository;
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;


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

        MemberFormDto mDto = MemberFormDto.builder()
                .loginId("1")
                .name("1")
                .password("1")
                .build();

        memberService.save(mDto);
        Members member = memberService.findByLoginId("1");
        productService.save(productFormDto, member);

//        List<ProductRecordDto> allProduct = productService.findAllProduct();
//        Product byId_product = productService.findById_Product(1L);

        ProductRecordDto productRecordDto = productService.findById_ProductRecordDto(1L);
        System.out.println(productRecordDto.getProductImagesList());
//        System.out.println(byId_product.getProductImagesList());
//        System.out.println( allProduct.get(0).getProductImagesList());

    }

    @Test
    void DB에물품많이넣기_테스트(){
        Product product = repository.findById((long) 1).get();

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
        Members members = memberRepository.findById((long) 1).get();

        try {
            for (int i = 0; i < 100; i++) {
                productService.save(test_product, members);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void 페이징처리_테스트() throws IOException{


        List<MultipartFile> multipartFiles = new ArrayList<MultipartFile>();
        multipartFiles.add(new MockMultipartFile("test1", "test1.PNG", MediaType.IMAGE_PNG_VALUE, "test1".getBytes(StandardCharsets.UTF_8)));

        PageRequest product = PageRequest.of(0, 3);
        for (int i = 0; i < 100; i++) {

            ProductFormDto test_product1 = ProductFormDto.builder()
                    .name( "test" + i )
                    .instancePrice((long) i+1000)
                    .auctionPrice((long)i)
                    .Explanation("test 설명" + i)
                    .category("패딩점퍼,여성의류")
                    .productTime(ProductTime.NONE)
                    .imageFileList(multipartFiles)
                    .build();

            ProductFormDto test_product2 = ProductFormDto.builder()
                    .name( "test2" + i )
                    .instancePrice((long) i+100)
                    .auctionPrice((long)i)
                    .Explanation("test 설명" + i)
                    .productTime(ProductTime.NONE)
                    .category("패딩점퍼,여성의류")
                    .imageFileList(multipartFiles)
                    .build();

            MemberFormDto mDto = MemberFormDto.builder()
                    .loginId(Integer.toString(i))
                    .name("1")
                    .password("1")
                    .build();

            memberService.save(mDto);
            Members member = memberService.findByLoginId(Integer.toString(i));
            productService.save( test_product1, member);
            productService.save( test_product2, member);

            if ( i > 0 ) {

                OrderBuyNowDto build = OrderBuyNowDto.builder()
                        .memberId((long) 1)
                        .productId((long) i)
                        .build();

                orderService.saveBuyNowDto(build);

            }
        }



        List<OrderHistoryDto> content = orderRepository.findMemberPurchaseOrder(1L, product).getContent();

        log.info("byProductId= {}",content.size());

        Page<ProductRecordDto> productRecordDtoList = productService.paged_product(product);
        System.out.println(productRecordDtoList);
        PageRequest product2 = PageRequest.of(1, 3);
        Page<ProductRecordDto> productRecordDtoList2 = productService.paged_product(product2);
        System.out.println(productRecordDtoList2);

    }

}
