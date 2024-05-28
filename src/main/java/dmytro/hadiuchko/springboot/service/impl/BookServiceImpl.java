package dmytro.hadiuchko.springboot.service.impl;

import dmytro.hadiuchko.springboot.dto.book.request.BookSearchParametersDto;
import dmytro.hadiuchko.springboot.dto.book.request.CreateBookRequestDto;
import dmytro.hadiuchko.springboot.dto.book.responce.BookDto;
import dmytro.hadiuchko.springboot.dto.book.responce.BookDtoWithoutCategoryIds;
import dmytro.hadiuchko.springboot.entity.Book;
import dmytro.hadiuchko.springboot.entity.Category;
import dmytro.hadiuchko.springboot.exception.EntityNotFoundException;
import dmytro.hadiuchko.springboot.mapper.BookMapper;
import dmytro.hadiuchko.springboot.repository.BookRepository;
import dmytro.hadiuchko.springboot.repository.CategoryRepository;
import dmytro.hadiuchko.springboot.repository.book.BookSpecificationBuilder;
import dmytro.hadiuchko.springboot.service.BookService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder specificationBuilder;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
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
    @Transactional
    public BookDto updateById(Long id, CreateBookRequestDto bookRequestDto) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id: " + id));
        book.setTitle(bookRequestDto.getTitle());
        book.setAuthor(bookRequestDto.getAuthor());
        book.setIsbn(bookRequestDto.getIsbn());
        book.setPrice(bookRequestDto.getPrice());
        book.setDescription(bookRequestDto.getDescription());
        book.setCoverImage(bookRequestDto.getCoverImage());
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> search(BookSearchParametersDto searchParameters, Pageable pageable) {
        Specification<Book> bookSpecification = specificationBuilder.build(searchParameters);
        return bookRepository.findAll(bookSpecification, pageable)
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryIds(Long categoriesId) {
        return bookRepository.findAllByCategoriesId(categoriesId)
                .stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        bookRepository.findById(id).ifPresentOrElse(bookRepository::delete, () -> {
            throw new EntityNotFoundException("Can't find book by id " + id);
        });
    }
}
