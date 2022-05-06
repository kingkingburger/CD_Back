package SilkLoad.example.service;

import SilkLoad.entity.Product;
import SilkLoad.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Test
    void 물품_가져오기_테스트(){
        List<Product> allProduct = productService.findAllProduct();
        Product byId_product = productService.findById_Product((long) 5);
//        System.out.println("byId_product = " + byId_product);
        for (Product product : allProduct) {
            System.out.println("product = " + product.getProductImagesList().get(0).getStoreFileName());
        }
    }
}
