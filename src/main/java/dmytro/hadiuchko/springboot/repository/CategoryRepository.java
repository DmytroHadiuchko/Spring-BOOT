package dmytro.hadiuchko.springboot.repository;

import dmytro.hadiuchko.springboot.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
