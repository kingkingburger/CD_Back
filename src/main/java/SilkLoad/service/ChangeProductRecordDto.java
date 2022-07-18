package SilkLoad.service;

import SilkLoad.dto.CategoryRecordDto;
import SilkLoad.dto.ProductImageRecordDto;
import SilkLoad.dto.ProductRecordDto;
import SilkLoad.entity.Category;
import SilkLoad.entity.Product;
import SilkLoad.entity.ProductEnum.ProductTime;
import SilkLoad.entity.ProductImage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ChangeProductRecordDto {
    private CategoryRecordDto CategoryToDto(Category category) {

        return CategoryRecordDto.builder()
                .first(category.getFirst())
                .second(category.getSecond())
                .third(category.getThird())
                .build();
    }

    public LocalDateTime productDeadLine(LocalDateTime createdProduct, ProductTime productTime) {
        if (productTime == ProductTime.ONE_DAY) {
            createdProduct = createdProduct.plusDays(1);
        } else if (productTime == ProductTime.TWO_DAY) {
            createdProduct = createdProduct.plusDays(2);
        }

        return createdProduct;
    }
    /**
     * ProductImage의 List를 Dto로 만드는 메서드
     *
     * @param productImageList
     * @return
     */
    @Transactional
    List<ProductImageRecordDto> ImageListToDto(List<ProductImage> productImageList) {

        List<ProductImageRecordDto> productImageRecordDtoList = new ArrayList<ProductImageRecordDto>();

        productImageList.forEach(productImage -> {
            ProductImageRecordDto buildImage = ProductImageRecordDto.builder()
                    .storeFileName(productImage.getStoreFileName())
                    .uploadFileName(productImage.getStoreFileName())
                    .build();

            productImageRecordDtoList.add(buildImage);
        });

        return productImageRecordDtoList;
    }

    /**
     * 매개변수 product를 통해 ProductRecordDto를 생성
     */
    @Transactional
    public ProductRecordDto ProductToProductRecordDto(Product product) {

        ProductRecordDto productRecordDto = ProductRecordDto.builder()
                .id(product.getId())
                .name(product.getName())
                .auctionPrice(product.getAuctionPrice())
                .instantPrice(product.getInstantPrice())
                .explanation(product.getExplanation())
                .productType(product.getProductType())
                .categoryRecordDto(CategoryToDto(product.getCategory()))
                .deadLine(productDeadLine(product.getCreatedDate(), product.getProductTime()))
                .productTime(product.getProductTime())
                .productImagesList(ImageListToDto(product.getProductImagesList()))
                .build();

        return productRecordDto;
    }
}
