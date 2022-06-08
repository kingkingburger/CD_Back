package SilkLoad.repository;

import SilkLoad.dto.*;
import SilkLoad.entity.Product;
import SilkLoad.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;
    @Autowired
    OrderRepository orderRepository;


    @Test
    void pagination() {
        PageRequest pageRequest = PageRequest.of(1,8);
        PageRequest next = pageRequest.next();
        pageRequest.getPageSize();
        List<ProductRecordDto> content = productService.paged_product(pageRequest).getContent();
        System.out.println("content = " + content);
        productService.paged_product(pageRequest).getPageable().getPageSize();

//        int number = productService.paged_product(pageRequest).getNumberOfElements();
//        System.out.println("현제 페이지  = " + number);

//        List<ProductRecordDto> next_content = productService.paged_product(next).getContent();
//        System.out.println("next_content = " + next_content);


    }

    @Test
    @Transactional
    void 카테고리별로_product가져오기_테스트(){
        PageRequest pageRequest = PageRequest.of(0,2);
        Page<ProductCategoryDto> 예술 = productRepository.findD("신발",pageRequest);

        for (ProductCategoryDto productCategoryDto : 예술) {
            List<ProductImageRecordDto> list = new ArrayList<>();

            String first = productCategoryDto.getFirst();
            String second = productCategoryDto.getSecond();
            CategoryRecordDto build = CategoryRecordDto.builder().first(first).second(second).build();

            ProductImageRecordDto imageRecordDto = ProductImageRecordDto.builder()
                    .storeFileName(productCategoryDto.getStoreFileName())
                    .uploadFileName(productCategoryDto.getUploadFileName())
                    .build();
            list.add(imageRecordDto);

            ProductRecordDto productRecordDto = ProductRecordDto.builder()
                    .id(productCategoryDto.getId())
                    .name(productCategoryDto.getName())
                    .auctionPrice(productCategoryDto.getAuctionPrice())
                    .instantPrice(productCategoryDto.getInstantPrice())
                    .explanation(productCategoryDto.getExplanation())
                    .productType(productCategoryDto.getProductType())
                    .categoryRecordDto(build)
                    .deadLine(productService.productDeadLine(productCategoryDto.getCreatedDate(), productCategoryDto.getProductTime()))
                    .productTime(productCategoryDto.getProductTime())
                    .productImagesList(list)
                    .build();
            System.out.println("productRecordDto = " + productRecordDto);
        }
    }

}