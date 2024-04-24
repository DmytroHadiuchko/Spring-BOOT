package dmytro.hadiuchko.springboot.service.impl;

import dmytro.hadiuchko.springboot.dto.request.BookSearchParametersDto;
import dmytro.hadiuchko.springboot.dto.request.CreateBookRequestDto;
import dmytro.hadiuchko.springboot.dto.response.BookDto;
import dmytro.hadiuchko.springboot.entity.Book;
import dmytro.hadiuchko.springboot.exception.EntityNotFoundException;
import dmytro.hadiuchko.springboot.mapper.BookMapper;
import dmytro.hadiuchko.springboot.repository.BookRepository;
import dmytro.hadiuchko.springboot.repository.book.BookSpecificationBuilder;
import dmytro.hadiuchko.springboot.service.BookService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder specificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto findById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find book by id: " + id));
    }

    @Override
    public void updateById(Long id, CreateBookRequestDto bookRequestDto) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: " + id));
        book.setTitle(bookRequestDto.getTitle());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setPrice(bookRequestDto.getPrice());
        book.setDescription(bookRequestDto.getDescription());
        book.setCoverImage(bookRequestDto.getCoverImage());
        bookRepository.save(book);
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto searchParameters) {
        Specification<Book> bookSpecification = specificationBuilder.build(searchParameters);
        return bookRepository.findAll(bookSpecification)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.findById(id).ifPresentOrElse(bookRepository::delete, () -> {
            throw new EntityNotFoundException("Can't find book by id " + id);
        });
    }
}
