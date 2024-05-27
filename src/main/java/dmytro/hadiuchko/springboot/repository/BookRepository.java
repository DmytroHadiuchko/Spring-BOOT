package dmytro.hadiuchko.springboot.repository;

import dmytro.hadiuchko.springboot.entity.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @Query("FROM Book b JOIN b.categories c WHERE c.id = :categoryId")
    List<Book> findAllByCategoriesId(@Param("categoryId") Long categoryId);
}
