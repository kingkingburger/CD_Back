package SilkLoad.repository;

import SilkLoad.entity.Crawling;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CrawlingRepositoryTest {

    @Autowired
    private CrawlingRepository crawlingRepository;

    @Test
    void 크롤링데이터가져오기_테스트(){

//        List<Crawling> 여성의류 = crawlingRepository.findByCategory("여성의류");
//        for (Crawling crawling : 여성의류) {
//            System.out.println("crawling = " + crawling);
//        }
    }
}