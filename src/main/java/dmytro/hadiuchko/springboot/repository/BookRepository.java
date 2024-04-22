package dmytro.hadiuchko.springboot.repository;

import dmytro.hadiuchko.springboot.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
