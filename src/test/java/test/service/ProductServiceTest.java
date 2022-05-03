package test.service;

import SilkLoad.DemoApplication;
import SilkLoad.entity.Product;
import SilkLoad.entity.ProductImage;
import SilkLoad.repository.ProductImageRepository;
import SilkLoad.repository.ProductRepository;
import SilkLoad.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

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

        //foreach 방식으로 storeFile 찾기
        List<Product> allProduct = service.findAllProduct();
        allProduct.forEach(product -> System.out.println("product = " + product.getProductImagesList().get(0).getStoreFileName()));

    }

    //id를 가지고 product를 가져오기
    @Test
    public void take_product_byid(){

        //foreach 방식으로 storeFile 찾기
        Product byId_product = service.findById_Product((long) 17);
        System.out.println("byId_product = " + byId_product.getExplanation());
    }



}
