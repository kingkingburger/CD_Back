package SilkLoad.repository;

import SilkLoad.entity.Crawling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrawlingRepository extends JpaRepository<Crawling, Long> {

}
