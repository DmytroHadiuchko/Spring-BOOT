package dmytro.hadiuchko.springboot.service;

import dmytro.hadiuchko.springboot.entity.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List findAll();
}
