package dmytro.hadiuchko.springboot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dmytro.hadiuchko.springboot.dto.book.request.CreateBookRequestDto;
import dmytro.hadiuchko.springboot.dto.book.responce.BookDto;
import dmytro.hadiuchko.springboot.entity.Book;
import dmytro.hadiuchko.springboot.repository.BookRepository;
import dmytro.hadiuchko.springboot.service.BookService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    private static final String NEW_AUTHOR = "Harper Lee";
    private static final String NEW_TITLE = "To Kill a Mockingbird";
    private static final String NEW_ISBN = "978-3-16-148410-0";
    private static final BigDecimal NEW_PRICE = BigDecimal.valueOf(10.99);
    private static final String NEW_DESCRIPTION = "interesting description.";
    private static final String NEW_COVER_IMAGE = "tokillamockingbird.jpg";
    private static final Long BOOK_ID = 1L;
    private static final String BOOK_TITLE = "some title";
    private static final String BOOK_URL = "/api/books";
    private static final String BOOK_BY_ID_URL = "/api/books/1";
    private static final BigDecimal SECOND_PRICE = BigDecimal.valueOf(150);
    private static final Long BOOK_SECOND_ID = 2L;
    private static final String SECOND_TITLE = "Second Title";
    private static final String SECOND_AUTHOR = "Second Author";
    private static final String SECOND_ISBN = "Second isbn";
    private static final String SECOND_COVER_IMAGE = "Second cover image";
    private static final String SECOND_DESCRIPTION = "Second description";

    private static MockMvc mockMvc;

    private CreateBookRequestDto requestDto;
    private BookDto expected;

    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private BookService bookService;
    @Autowired
    private BookRepository bookRepository;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @BeforeEach
    void setUp() {
        requestDto = new CreateBookRequestDto();
        requestDto.setTitle(NEW_TITLE);
        requestDto.setAuthor(NEW_AUTHOR);
        requestDto.setPrice(NEW_PRICE);
        requestDto.setIsbn(NEW_ISBN);
        requestDto.setCoverImage(NEW_COVER_IMAGE);
        requestDto.setDescription(NEW_DESCRIPTION);

        expected = new BookDto();
        expected.setId(BOOK_ID);
        expected.setTitle(requestDto.getTitle());
        expected.setAuthor(requestDto.getAuthor());
        expected.setPrice(requestDto.getPrice());
        expected.setIsbn(requestDto.getIsbn());
        expected.setDescription(requestDto.getDescription());
        expected.setCoverImage(requestDto.getCoverImage());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("create a new book")
    void createBook_ValidRequestDto_Success() throws Exception {
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        String result = mockMvc.perform(post(BOOK_URL)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        BookDto actual = objectMapper.readValue(result, BookDto.class);

        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @Sql(scripts = "classpath:sql/scripts/insert-into-book.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/scripts/drop-tables.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user", roles = {"ADMIN", "USER"})
    @DisplayName("Return all books with valid role")
    void getAllBooks_withUserRole_ReturnAll() throws Exception {
        List<BookDto> bookDtoList = getBookDtos();

        String result = mockMvc.perform(get(BOOK_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<BookDto> actual = objectMapper.readValue(result,
                new TypeReference<List<BookDto>>() {});
        assertNotNull(actual);
        assertEquals(bookDtoList.size(), actual.size());
        for (int i = 0; i < bookDtoList.size(); i++) {
            EqualsBuilder.reflectionEquals(bookDtoList.get(i), actual.get(i), "id");
        }
    }

    @Test
    @Sql(scripts = "classpath:sql/scripts/insert-into-book.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/scripts/drop-tables.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user", roles = {"ADMIN", "USER"})
    @DisplayName("Return book by id with valid role")
    void getBookById_withUserRole_ReturnBook() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setId(BOOK_ID);
        bookDto.setTitle(BOOK_TITLE);

        mockMvc.perform(get(BOOK_BY_ID_URL))
                .andExpect(status().isOk());
    }

    @Test
    @Sql(scripts = "classpath:sql/scripts/insert-into-book.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:sql/scripts/drop-tables.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Delete book by id with valid role")
    void deleteBook_WithAdminRole_Success() throws Exception {
        mockMvc.perform(delete(BOOK_BY_ID_URL, BOOK_ID))
                .andExpect(status().isNoContent());
        Optional<Book> deleteBook = bookRepository.findById(BOOK_ID);
        assertTrue(deleteBook.isEmpty());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("Delete book by id with invalid role")
    void deleteBook_WIthInValidRoleUser_unsuccess() throws Exception {
        mockMvc.perform(delete(BOOK_BY_ID_URL))
                .andExpect(status().isForbidden());
    }

    private List<BookDto> getBookDtos() {
        BookDto firstBook = new BookDto();
        firstBook.setId(BOOK_ID);
        firstBook.setTitle(NEW_TITLE);
        firstBook.setAuthor(NEW_AUTHOR);
        firstBook.setIsbn(NEW_ISBN);
        firstBook.setCoverImage(NEW_COVER_IMAGE);
        firstBook.setDescription(NEW_DESCRIPTION);
        firstBook.setPrice(NEW_PRICE);

        BookDto secondBook = new BookDto();
        secondBook.setId(BOOK_SECOND_ID);
        secondBook.setTitle(SECOND_TITLE);
        secondBook.setAuthor(SECOND_AUTHOR);
        secondBook.setIsbn(SECOND_ISBN);
        secondBook.setCoverImage(SECOND_COVER_IMAGE);
        secondBook.setDescription(SECOND_DESCRIPTION);
        secondBook.setPrice(SECOND_PRICE);

        return List.of(secondBook, firstBook);
    }
}
