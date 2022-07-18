package SilkLoad.service;

import SilkLoad.dto.CategoryRecordDto;
import SilkLoad.dto.ProductImageRecordDto;
import SilkLoad.dto.ProductRecordDto;
import SilkLoad.entity.Category;
import SilkLoad.entity.Product;
import SilkLoad.entity.ProductEnum.ProductTime;
import SilkLoad.entity.ProductEnum.ProductType;
import SilkLoad.entity.ProductImage;
import SilkLoad.repository.CategoryRepository;
import SilkLoad.repository.OrderRepository;
import SilkLoad.repository.ProductImageRepository;
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
public class PagedProductService {

    private final ProductRepository productRepository;
    private final ChangeProductRecordDto changeProductRecordDto;


    /**
     * 테이블에 product 를 가져와서 map으로 ProductRecordDto로 만들어준다.
     *
     * @param pageable
     * @return
     */
    @Transactional
    public Page<ProductRecordDto> paged_product(Pageable pageable) {
        Page<ProductRecordDto> sale = productRepository
                .findByProductTypeOrderByIdDesc(ProductType.sale, pageable)
                .map(changeProductRecordDto::ProductToProductRecordDto);

        return sale;
    }
}
