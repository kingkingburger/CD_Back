package SilkLoad.service;

import SilkLoad.dto.ProductRecordDto;
import SilkLoad.entity.ProductEnum.ProductType;
import SilkLoad.repository.CrawlingRepository;
import SilkLoad.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductSearchService {
    private final ProductRepository productRepository;
    private final CrawlingRepository crawlingRepository;
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
