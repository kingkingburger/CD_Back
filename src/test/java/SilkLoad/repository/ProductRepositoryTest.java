package SilkLoad.repository;

import SilkLoad.dto.ProductRecordDto;
import SilkLoad.entity.Product;
import SilkLoad.entity.ProductEnum.ProductType;
import SilkLoad.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;

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
    void 타입별로모두가져오기_테스트() {
        productRepository.findAllByProductType(ProductType.sale);
    }

    @Test
    void 카테고리별로_product가져오기_테스트(){
        List<Product> all = productRepository.findA("예술");

        for (Product product : all) {
            System.out.println("product = " + product);
        }
    }
}