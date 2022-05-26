package SilkLoad.repository;

import SilkLoad.entity.Cart;
import SilkLoad.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByMember(Members memberid);


    @Transactional
    void deleteByProductId(Long productid);

}
