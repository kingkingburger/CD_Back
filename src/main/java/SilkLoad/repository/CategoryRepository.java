package SilkLoad.repository;

import SilkLoad.entity.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @EntityGraph("CategoryWithProduct")
    List<Category> findAll();

    //1차,2차,3차 카테고리를 받아서 Category엔티티를 반환한다.
    //거기에는 productList가 들어있다.
    @EntityGraph("CategoryWithProduct")
    Optional<Category> findTopByFirstAndSecondAndThird(String first, String second, String third);
    @EntityGraph("CategoryWithProduct")
    Optional<Category> findByFirstAndSecondAndThird(String first, String second, String third);
}
