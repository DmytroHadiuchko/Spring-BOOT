package dmytro.hadiuchko.springboot.service;

import dmytro.hadiuchko.springboot.dto.request.BookSearchParametersDto;
import dmytro.hadiuchko.springboot.dto.request.CreateBookRequestDto;
import dmytro.hadiuchko.springboot.dto.response.BookDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void deleteById(Long id);

    void updateById(Long id, CreateBookRequestDto bookRequestDto);

    List<BookDto> search(BookSearchParametersDto searchParameters, Pageable pageable);
}
