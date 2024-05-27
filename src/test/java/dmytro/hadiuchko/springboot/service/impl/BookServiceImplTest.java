package dmytro.hadiuchko.springboot.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dmytro.hadiuchko.springboot.dto.book.request.CreateBookRequestDto;
import dmytro.hadiuchko.springboot.dto.book.responce.BookDto;
import dmytro.hadiuchko.springboot.dto.book.responce.BookDtoWithoutCategoryIds;
import dmytro.hadiuchko.springboot.entity.Book;
import dmytro.hadiuchko.springboot.exception.EntityNotFoundException;
import dmytro.hadiuchko.springboot.mapper.BookMapper;
import dmytro.hadiuchko.springboot.repository.BookRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    private static final String NEW_AUTHOR = "Harper Lee";
    private static final String NEW_TITLE = "To Kill a Mockingbird";
    private static final String NEW_ISBN = "978-3-16-148410-0";
    private static final BigDecimal NEW_PRICE = BigDecimal.valueOf(10.99);
    private static final String NEW_DESCRIPTION = "interesting description.";
    private static final String NEW_COVER_IMAGE = "tokillamockingbird.jpg";
    private static final Long BOOK_ID = 1L;
    private static final String BOOK_TITLE = "some title";
    private static final String UPDATED_BOOK_TITLE = "updated book's title";
    private static final int PAGE_SIZE = 10;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Save a new book with valid values")
    void save_validRequestDto_bookSaved() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setAuthor(NEW_AUTHOR);
        requestDto.setTitle(NEW_TITLE);
        requestDto.setIsbn(NEW_ISBN);
        requestDto.setPrice(NEW_PRICE);
        requestDto.setDescription(NEW_DESCRIPTION);
        requestDto.setCoverImage(NEW_COVER_IMAGE);

        BookDto bookDto = new BookDto();
        bookDto.setAuthor(requestDto.getAuthor());
        bookDto.setTitle(requestDto.getTitle());
        bookDto.setIsbn(requestDto.getIsbn());
        bookDto.setPrice(requestDto.getPrice());
        bookDto.setDescription(requestDto.getDescription());
        bookDto.setCoverImage(requestDto.getCoverImage());

        Book book = new Book();

        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.save(requestDto);

        assertEquals(bookDto, result);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @DisplayName("Find all books")
    void findAll_validPageable_booksFound() {
        Book book = new Book();
        book.setId(BOOK_ID);
        book.setTitle(NEW_TITLE);

        List<Book> books = new ArrayList<>();
        books.add(book);

        BookDto bookDto = new BookDto();
        bookDto.setTitle(NEW_TITLE);

        List<BookDto> bookDtos = new ArrayList<>();
        bookDtos.add(bookDto);

        Page<Book> bookPage = new PageImpl<>(books);
        Pageable pageable = Pageable.ofSize(PAGE_SIZE);

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toDto(any(Book.class))).thenReturn(bookDtos.get(0));

        List<BookDto> result = bookService.findAll(pageable);

        assertEquals(bookDtos, result);
        assertEquals(NEW_TITLE, bookDtos.get(0).getTitle());
        assertEquals(1, bookDtos.size());
        verify(bookRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Find book by id")
    void findById_validId_bookFound() {
        Book book = new Book();
        book.setId(BOOK_ID);
        book.setTitle(NEW_TITLE);
        book.setIsbn((NEW_ISBN));
        book.setPrice(NEW_PRICE);
        book.setDescription((NEW_DESCRIPTION));
        book.setCoverImage((NEW_COVER_IMAGE));

        BookDto bookDto = new BookDto();
        bookDto.setTitle(book.getTitle());

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.findById(book.getId());

        assertEquals(bookDto, result);
        assertEquals(NEW_TITLE, result.getTitle());
    }

    @Test
    @DisplayName("Find book with invalid id")
    void findById_invalidId_entityNotFoundExceptionThrown() {
        Long id = 1L;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            bookService.findById(id);
        });

        assertEquals("Can't find book by id: " + id, exception.getMessage());
    }

    @Test
    @DisplayName("Update book by id")
    void updateById_validIdAndRequestDto_bookUpdated() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setCoverImage(NEW_COVER_IMAGE);
        requestDto.setTitle(UPDATED_BOOK_TITLE);
        requestDto.setIsbn(NEW_ISBN);
        requestDto.setPrice(NEW_PRICE);
        requestDto.setDescription(NEW_DESCRIPTION);
        requestDto.setAuthor(NEW_AUTHOR);

        Book book = new Book();
        book.setId(BOOK_ID);
        book.setTitle(BOOK_TITLE);

        BookDto bookDto = new BookDto();
        bookDto.setAuthor(requestDto.getAuthor());
        bookDto.setTitle(requestDto.getTitle());
        bookDto.setIsbn(requestDto.getIsbn());
        bookDto.setPrice(requestDto.getPrice());
        bookDto.setDescription(requestDto.getDescription());
        bookDto.setCoverImage(requestDto.getCoverImage());

        Long id = 1L;
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto updatedBook = bookService.updateById(id, requestDto);
        assertEquals(UPDATED_BOOK_TITLE, updatedBook.getTitle());
    }

    @Test
    @DisplayName("Update book with invalid id")
    void updateById_invalidId_entityNotFoundExceptionThrown() {
        Long id = 1L;
        CreateBookRequestDto requestDto = new CreateBookRequestDto();

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            bookService.updateById(id, requestDto);
        });

        assertEquals("Can't find book by id: " + id, exception.getMessage());
        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Delete book by id")
    void deleteById_validId_bookDeleted() {
        Book book = new Book();
        book.setId(BOOK_ID);
        book.setTitle(BOOK_TITLE);
        bookRepository.save(book);

        when(bookRepository.findById(BOOK_ID)).thenReturn(Optional.of(book));

        bookService.deleteById(BOOK_ID);

        verify(bookRepository, times(1)).delete(book);
    }

    @Test
    @DisplayName("Delete book by invalid id")
    void deleteById_invalidId_entityNotFoundExceptionThrown() {
        Long id = 1L;

        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            bookService.deleteById(id);
        });

        assertEquals("Can't find book by id " + id, exception.getMessage());
        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Return book by category id")
    void getBooksByCategoryIds_ValidCategoryId_BooksFound() {
        Long categoryId = 1L;
        List<Book> bookList = Collections.singletonList(new Book());

        when(bookRepository.findAllByCategoriesId(categoryId)).thenReturn(bookList);
        when(bookMapper.toDtoWithoutCategories(any(Book.class)))
                .thenReturn(new BookDtoWithoutCategoryIds());
        List<BookDtoWithoutCategoryIds> result = bookService.getBooksByCategoryIds(categoryId);

        assertEquals(1, result.size());
        verify(bookRepository, times(1)).findAllByCategoriesId(categoryId);
    }
}
