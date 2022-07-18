package SilkLoad.service;

import SilkLoad.dto.CategoryRecordDto;
import SilkLoad.dto.ProductImageRecordDto;
import SilkLoad.dto.ProductRecordDto;
import SilkLoad.entity.Category;
import SilkLoad.entity.Product;
import SilkLoad.entity.ProductEnum.ProductTime;
import SilkLoad.entity.ProductEnum.ProductType;
import SilkLoad.entity.ProductImage;
import SilkLoad.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSearchService {
    private final ProductRepository productRepository;
    private final ChangeProductRecordDto changeProductRecordDto;

    /**
     * keyword를 기준으로 Paging된 productRecordDto를 반환
     *
     * @param keyword
     * @param pageable
     * @return
     */
    @Transactional
    public Page<ProductRecordDto> SearchToProductname(String keyword, Pageable pageable) {
        Page<ProductRecordDto> productRecordDtoPage = productRepository
                .findByNameContainingIgnoreCaseAndProductType(keyword, ProductType.sale, pageable)
                .map(changeProductRecordDto::ProductToProductRecordDto);
        return productRecordDtoPage;
    }
}
