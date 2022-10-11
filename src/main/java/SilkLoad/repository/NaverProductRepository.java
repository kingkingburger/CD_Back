package SilkLoad.repository;


import SilkLoad.dto.NaverProductDto;
import SilkLoad.entity.Crawling;
import SilkLoad.entity.NaverProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NaverProductRepository extends JpaRepository<NaverProduct, Long> {

    List<NaverProduct> findByCategory(String category);

    Page<NaverProduct> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

}
