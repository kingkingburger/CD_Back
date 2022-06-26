package SilkLoad.repository;

import SilkLoad.dto.ProductFormDto;
import SilkLoad.entity.Category;
import SilkLoad.entity.Members;
import SilkLoad.entity.Product;
import SilkLoad.service.MemberService;
import SilkLoad.service.OrderService;
import SilkLoad.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class CategoryRepositoryTest {
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    @Transactional
    @Rollback(value = false)
    void 카테고리_물품넣기_테스트() throws IOException {

//        Product product = productRepository.findById((long) 1).get();

        //더미 이미지 만들기, jpg -> MultipartFile로 만들기가 힘들다
        List<MultipartFile> imageFiles = List.of(
                new MockMultipartFile("7a88ca37-9e3b-4be3-af72-1bd6e5b7fe9f.jpg",
                        "7a88ca37-9e3b-4be3-af72-1bd6e5b7fe9f.jpg",
                        MediaType.IMAGE_JPEG_VALUE,
                        "test1".getBytes())
        );
        //새로운 물품 만들기
        ProductFormDto test_product = ProductFormDto.builder()
                .name("test입니다.")
                .instancePrice(1234L)
                .auctionPrice(1234L)
                .Explanation("test입니다.")
                .category("a,b")
                .imageFileList(imageFiles)
                .build();

        //DB에 있는 memeber 가지고 오기, member가 정해지지 않으면 영속성에서 외래키 오류가 발생한다
        Members members = memberRepository.findById((long) 1).get();
        productService.save(test_product, members);

        List<Category> all = categoryRepository.findByFirst("a");
        categoryRepository.save(all.get(0));
        all.forEach(System.out::println);

    }
}