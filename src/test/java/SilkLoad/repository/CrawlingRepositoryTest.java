package SilkLoad.repository;

import SilkLoad.dto.CrawlingDto;
import SilkLoad.entity.Crawling;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CrawlingRepositoryTest {

    @Autowired
    private CrawlingRepository crawlingRepository;

    public CrawlingDto ToCrawlingDto(Crawling crawling) {
        CrawlingDto crawlingDto = CrawlingDto.builder()
                .name(crawling.getName())
                .category(crawling.getFirst())
                .img_link(crawling.getImg_link())
                .link(crawling.getLink())
                .price(crawling.getPrice())
                .build();
        return crawlingDto;
    }

    @Test
    @Transactional(readOnly = true)
    void 크롤링데이터가져오기_테스트() {
        PageRequest pageRequest = PageRequest.of(1, 8);

        Page<CrawlingDto> data = crawlingRepository
                .findByFirstAndSecondAndThird("여성의류", "코트", "봄가을", pageRequest)
                .map(this::ToCrawlingDto);
        System.out.println("data = " + data.getContent());

    }


}