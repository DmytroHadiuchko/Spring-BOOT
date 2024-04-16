package dmytro.hadiuchko.springboot.repository;

import dmytro.hadiuchko.springboot.entity.Book;
import java.util.List;

public interface BookRepository {
    Book save(Book book);

    List findAll();
}
