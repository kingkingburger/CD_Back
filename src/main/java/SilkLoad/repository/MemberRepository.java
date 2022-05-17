package SilkLoad.repository;

import SilkLoad.entity.Members;
import SilkLoad.entity.Product;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Members, Long>{

    Optional<Members> findByLoginId(String LoginId);

    @Query("select distinct m from Members m left join fetch m.productList")
    List<Members> findAllJPQLFetch();


}
