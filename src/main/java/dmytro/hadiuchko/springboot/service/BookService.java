package dmytro.hadiuchko.springboot.service;

import dmytro.hadiuchko.springboot.dto.book.request.BookSearchParametersDto;
import dmytro.hadiuchko.springboot.dto.book.request.CreateBookRequestDto;
import dmytro.hadiuchko.springboot.dto.book.responce.BookDto;
import dmytro.hadiuchko.springboot.dto.book.responce.BookDtoWithoutCategoryIds;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto findById(Long id);

    void deleteById(Long id);

    BookDto updateById(Long id, CreateBookRequestDto bookRequestDto);

    List<BookDto> search(BookSearchParametersDto searchParameters, Pageable pageable);

    List<BookDtoWithoutCategoryIds> getBooksByCategoryIds(Long categoriesId);
}
