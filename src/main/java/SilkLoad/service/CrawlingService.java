package SilkLoad.service;

import SilkLoad.dto.CrawlingDto;
import SilkLoad.entity.Crawling;
import SilkLoad.repository.CrawlingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CrawlingService {

    private final CrawlingRepository crawlingRepository;

    /**
     * table을 Dto로 만드는 메서드
     * @param crawling
     * @return
     */
    public CrawlingDto ToCrawlingDto(Crawling crawling){
        CrawlingDto crawlingDto = CrawlingDto.builder()
                .name(crawling.getName())
                .category(crawling.getFirst())
                .img_link(crawling.getImg_link())
                .link(crawling.getLink())
                .price(crawling.getPrice())
                .build();
        return crawlingDto;
    }

    //crawling 데이터를 first 기준으로 데이터 리턴(home 페이지에서 사용)
    @Transactional(readOnly = true)
    public Page<CrawlingDto> getcrawlingdatafirst(Pageable pageable, String first) {

        Page<CrawlingDto> data = crawlingRepository
                                    .findByFirst(first, pageable)
                                    .map(this::ToCrawlingDto);
        return data;
    }

    //crawling 데이터를 first, second 데이터 리턴 (shop 페이지에서 사용)
    @Transactional(readOnly = true)
    public Page<CrawlingDto> getcrawlingdatafirstandsecond(Pageable pageable, String first, String second) {
        Page<CrawlingDto> data = crawlingRepository
                .findByFirstAndSecond(first, second , pageable)
                .map(this::ToCrawlingDto);
        return data;
    }

    //crawling 데이터를 first, second third기준으로 데이터 리턴 (detail 페이지에서 사용)
    @Transactional(readOnly = true)
    public Page<CrawlingDto> getcrawlingdataFirstSecondThird(Pageable pageable,String first, String second ,String third) {
        Page<CrawlingDto> data = crawlingRepository
                .findByFirstAndSecondAndThirdContains(first, second, third, pageable)
                .map(this::ToCrawlingDto);
        return data;
    }
}
