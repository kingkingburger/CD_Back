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
    public CrawlingDto getCrawlingDtoList(Crawling crawling){

        CrawlingDto crawlingDto = CrawlingDto.builder()
                .name(crawling.getName())
                .category(crawling.getFirst())
                .img_link(crawling.getImg_link())
                .link(crawling.getLink())
                .price(crawling.getPrice())
                .build();

        return crawlingDto;
    }

    /**
     * 크롤링 데이터를 카태고리 별로 가지고 온다.
     * @param pageable
     * @param first
     * @return
     */
    @Transactional(readOnly = true)
    public Page<CrawlingDto> getcrawlingdatafirst(Pageable pageable, String first) {

        Page<CrawlingDto> data = crawlingRepository
                                    .findByFirst(first, pageable)
                                    .map(this::getCrawlingDtoList);
        log.info("first 쪽 data가 잘 들어오는지 ={}",data);
        return data;
    }
    @Transactional(readOnly = true)
    public Page<CrawlingDto> getcrawlingdatasecond(Pageable pageable, String second) {
        Page<CrawlingDto> data = crawlingRepository
                .findBySecond(second, pageable)
                .map(this::getCrawlingDtoList);
        log.info("second 쪽 data가 잘 들어오는지 ={}",data);
        return data;
    }

}
