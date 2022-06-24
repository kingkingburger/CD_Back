package SilkLoad.repository;

import SilkLoad.entity.Category;
import SilkLoad.entity.ChatMessage;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @EntityGraph("CategoryWithProduct")
    List<Category> findAll();

    @EntityGraph("CategoryWithProduct")
    List<Category> findByFirst(String first);
}
