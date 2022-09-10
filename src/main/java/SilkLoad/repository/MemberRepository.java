package SilkLoad.repository;

import SilkLoad.entity.Members;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Members, Long>{

    Optional<Members> findByLoginId(String LoginId);

    Optional<Members> findByEmail(String email); // 이미 email을 통해 생성된 사용자인지 채크
    @Query("select distinct m from Members m left join fetch m.productList")
    List<Members> findAllJPQLFetch();

    //join fetch 쿼리문 oneToMany 를 불러올 때 사용금지
/*    @Query(value = "select distinct m from Members m left join fetch m.productList WHERE m.id = ?1",
            countQuery = "select count(m.id) from Members m where m.id = ?1")*/

    //jpql join 문
/*    @Query(value = "SELECT m from Members m join m.productList p ON :memberId = p.members.id  ORDER BY m.id DESC")
    Page<Members> findByJoinProductIdDesc(@Param("memberId") Long memberId , Pageable pageable);*/

}
