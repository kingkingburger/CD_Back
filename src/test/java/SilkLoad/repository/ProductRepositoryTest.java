package SilkLoad.repository;

import SilkLoad.entity.ProductEnum.ProductType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    void pagination() {

    }

    @Test
    void 타입별로모두가져오기_테스트() {
        productRepository.findAllByProductType(ProductType.sale);
    }
}