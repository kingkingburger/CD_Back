package SilkLoad.example.service;

import SilkLoad.dto.ProductRecordDto;
import SilkLoad.entity.Product;
import SilkLoad.entity.ProductEnum.ProductType;
import SilkLoad.repository.ProductImageRepository;
import SilkLoad.repository.ProductRepository;
import SilkLoad.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductImageRepository productImageRepository;

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

    @Test
    void 물품_DB에많이넣기_테스트(){
        Product byId_product = productService.findById_Product((long) 1);
//        ProductRecordDto productRecordDto = productService.getProductRecordDto(byId_product);

//        ProductFormDtoTest test_product = ProductFormDtoTest.builder()
//                .name(byId_product.getName())
//                .instancePrice(byId_product.getInstantPrice())
//                .auctionPrice(byId_product.getAuctionPrice())
//                .Explanation(byId_product.getExplanation())
//                .category("패딩점퍼,여성의류")
//                .imageFileList(productRecordDto.getProductImagesList())
//                .build();
//
//        Members test_member = Members.builder()
//                .name(byId_product.getMembers().getName())
//                .loginId(byId_product.getMembers().getLoginId())
//                .password(byId_product.getMembers().getPassword())
//                .numberPurchase(byId_product.getMembers().getNumberPurchase())
//                .ranks(byId_product.getMembers().getRanks())
//                .build();

//        byId_product.changeCategory(productService.categoryClassification("패딩점퍼,여성의류"));
        productRepository.save(byId_product);
        productImageRepository.save(byId_product.getProductImagesList().get(0));

        System.out.println("저장됨");
    }
}
