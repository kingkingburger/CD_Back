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
                .category(crawling.getCategory())
                .img_link(crawling.getImg_link())
                .link(crawling.getLink())
                .price(crawling.getPrice())
                .build();

        return crawlingDto;
    }

    /**
     * 크롤링 데이터를 카태고리 별로 가지고 온다.
     * @param pageable
     * @param category
     * @return
     */
    @Transactional(readOnly = true)
    public Page<CrawlingDto> getcrawlingdata(Pageable pageable, String category) {

        Page<CrawlingDto> data = crawlingRepository
                                    .findByCategory(category, pageable)
                                    .map(this::getCrawlingDtoList);
        return data;
    }


}
