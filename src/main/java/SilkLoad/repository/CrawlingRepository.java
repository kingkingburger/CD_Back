package SilkLoad.repository;

import SilkLoad.entity.Crawling;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

@Repository
public interface CrawlingRepository extends JpaRepository<Crawling, Long> {

    Page<Crawling> findByCategory(String category, Pageable pageable);



}
