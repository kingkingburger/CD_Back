package SilkLoad.service;

import SilkLoad.entity.NaverProduct;
import SilkLoad.repository.NaverProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
class NaverProductServiceTest {

    @Autowired
    public NaverProductRepository naverProductRepository;

    @Test
    void findByCategory() {
        List<NaverProduct> 여성의류 = naverProductRepository.findByCategory("여성의류");
        log.info("여성의류: {}", 여성의류.get(0));
    }
}