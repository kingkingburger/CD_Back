package SilkLoad.repository;

import SilkLoad.dto.TradeOrderDto;
import SilkLoad.entity.Product;
import SilkLoad.entity.ProductEnum.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    //ProductType이 sale인것을 가져오는 쿼리(pagenation 가능)
    //select * from Product limit ? ;
    Page<Product> findByProductTypeOrderByIdDesc(ProductType type, Pageable pageable);

    //ProductType이 sale인것을 가져오는 쿼리
    //select * from product where ProductType == sale;
    List<Product> findAllByProductType(ProductType type);



}

