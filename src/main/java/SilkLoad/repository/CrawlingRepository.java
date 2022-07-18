package SilkLoad.repository;

import SilkLoad.entity.Crawling;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

@Repository
public interface CrawlingRepository extends JpaRepository<Crawling, Long> {

    Page<Crawling> findByFirst(String first, Pageable pageable);
//    Page<Crawling> findBySecond(String second, Pageable pageable);
//    Page<Crawling> findByThird(String third, Pageable pageable);

    //first, second, third 카테고리를 기준으로 crwaling db에서 데이터 가져오는 쿼리
    Page<Crawling> findByFirstAndSecondAndThird(String first, String second, String third, Pageable pageable);

    //first, second,카테고리를 기준으로 crwaling db에서 데이터 가져오는 쿼리
    Page<Crawling> findByFirstAndSecond(String first, String second, Pageable pageable);

}
