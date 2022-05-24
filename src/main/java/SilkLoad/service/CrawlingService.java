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

    @Transactional(readOnly = true)
    public Page<CrawlingDto> getwomenclose(Pageable pageable) {

        Page<CrawlingDto> 여성의류 = crawlingRepository
                                    .findByCategory("여성의류", pageable)
                                    .map(this::getCrawlingDtoList);
        return 여성의류;
    }

    @Transactional(readOnly = true)
    public Page<CrawlingDto> getmenclose(Pageable pageable) {

        Page<CrawlingDto> 남성의류 = crawlingRepository
                .findByCategory("남성의류", pageable)
                .map(this::getCrawlingDtoList);

        return 남성의류;
    }

    @Transactional(readOnly = true)
    public Page<CrawlingDto> getshose(Pageable pageable) {

        Page<CrawlingDto> 신발 = crawlingRepository
                .findByCategory("신발", pageable)
                .map(this::getCrawlingDtoList);
        return 신발;
    }


}
