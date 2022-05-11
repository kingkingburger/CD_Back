package SilkLoad.example.service;

import SilkLoad.dto.ProductRecordDto;
import SilkLoad.entity.Product;
import SilkLoad.entity.ProductType;
import SilkLoad.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    ProductService productService;


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
        Product byId_product = productService.findById_Product((long) 5);
//        System.out.println("byId_product = " + byId_product);
        for (ProductRecordDto product : allProduct) {
            System.out.println("product = " + product.getProductImagesList().get(0).getStoreFileName());
        }
    }
}
