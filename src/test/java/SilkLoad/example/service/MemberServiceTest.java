package SilkLoad.example.service;


import SilkLoad.dto.MemberFormDto;
import SilkLoad.dto.ProductFormDto;
import SilkLoad.entity.Members;
import SilkLoad.entity.Product;
import SilkLoad.entity.ProductEnum.ProductTime;
import SilkLoad.repository.MemberRepository;
import SilkLoad.service.MemberService;
import SilkLoad.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.IIOException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Slf4j
class MemberServiceTest {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Autowired
    ProductService productService;

    @Test
    void findbyLoginid(){
        Optional<Members> byId = memberRepository.findById((long) 1);
//        byId.isPresent(member -> new MemberFormDto().
//                builder().
//                loginId(member).
//                build())
        System.out.println("byId = " + byId);
    }

    @Test
    void findAll() {
        List<Members> all = memberRepository.findAll();
        System.out.println("all = " + all);
        //조회
    }

    @Test
    void fetchJoin() throws IOException {


        for (int i = 0; i < 100; i++) {
            ProductFormDto productFormDto = new ProductFormDto();
            productFormDto.getImageFileList().add(new MockMultipartFile("test1","test1.PNG", MediaType.IMAGE_PNG_VALUE,"test1".getBytes(StandardCharsets.UTF_8)) );

            productFormDto.setCategory("옷,바지");
            productFormDto.setName("사과");
            productFormDto.setAuctionPrice(1L);
            productFormDto.setInstancePrice(2L);
            productFormDto.setExplanation("테스트 중");
            productFormDto.setProductTime(ProductTime.ONE_DAY);


            String x = Integer.toString(i);
            MemberFormDto mDto = MemberFormDto.builder().loginId(x)
                    .name(x)
                    .password("1234")
                    .build();

            memberService.save(mDto);
            Members member = memberService.findByLoginId(x);

            productService.save(productFormDto, member);
        }

        List<Members> allJPQLFetch = memberRepository.findAllJPQLFetch();
        System.out.println("-----------------------------------------------");
//        log.info("회원의 모든 물품 리스트 ={}", allJPQLFetch);

    }

}