package test.service;

import SilkLoad.DemoApplication;
import SilkLoad.entity.Product;
import SilkLoad.repository.ProductImageRepository;
import SilkLoad.repository.ProductRepository;
import SilkLoad.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest(classes = DemoApplication.class)
public class ProductServiceTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductImageRepository productImageRepository;
    @Autowired
    ProductService service;

    //img 하나씩 보내기 위한 테스트
    @Test
    public void take_img(){

        List<Product> allProduct = service.findAllProduct();
        allProduct.forEach(product -> System.out.println("product = " + product.getProductImagesList().get(0).getStoreFileName()));

    }

}
