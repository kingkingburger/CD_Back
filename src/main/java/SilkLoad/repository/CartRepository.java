package SilkLoad.repository;

import SilkLoad.entity.Cart;
import SilkLoad.entity.Members;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Page<Cart> findByMember(Members memberid, Pageable pageable);

    @Transactional
    void deleteByProductId(Long productid);

    boolean existsByProductId(Long productid);

}
