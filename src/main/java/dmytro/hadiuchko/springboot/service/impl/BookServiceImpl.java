package dmytro.hadiuchko.springboot.service.impl;

import dmytro.hadiuchko.springboot.entity.Book;
import dmytro.hadiuchko.springboot.repository.BookRepository;
import dmytro.hadiuchko.springboot.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    @Override
    public Book save(Book book) {
        return null;
    }

    @Override
    public List findAll() {
        return null;
    }
}
