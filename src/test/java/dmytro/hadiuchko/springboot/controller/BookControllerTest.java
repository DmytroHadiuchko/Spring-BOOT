package dmytro.hadiuchko.springboot.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import dmytro.hadiuchko.springboot.dto.book.request.CreateBookRequestDto;
import dmytro.hadiuchko.springboot.dto.book.responce.BookDto;
import dmytro.hadiuchko.springboot.service.BookService;
import java.math.BigDecimal;
import java.util.List;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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

    private static MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private BookService bookService;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @DisplayName("create a new book")
    void createBook_ValidRequestDto_Success() throws Exception {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle(NEW_TITLE);
        requestDto.setAuthor(NEW_AUTHOR);
        requestDto.setPrice(NEW_PRICE);
        requestDto.setIsbn(NEW_ISBN);
        requestDto.setCoverImage(NEW_COVER_IMAGE);
        requestDto.setDescription(NEW_DESCRIPTION);

        BookDto expected = new BookDto();
        expected.setId(BOOK_ID);
        expected.setTitle(requestDto.getTitle());
        expected.setAuthor(requestDto.getAuthor());
        expected.setPrice(requestDto.getPrice());
        expected.setIsbn(requestDto.getIsbn());
        expected.setDescription(requestDto.getDescription());
        expected.setCoverImage(requestDto.getCoverImage());

        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        MvcResult result = mockMvc.perform(post(BOOK_URL)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andReturn();
        BookDto actual = objectMapper.readValue(result.getResponse()
                .getContentAsString(), BookDto.class);

        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(expected, actual, "id");
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN", "USER"})
    @DisplayName("Return all books with valid role")
    void getAllBooks_withUserRole_ReturnAll() throws Exception {
        List<BookDto> bookDtoList = List.of(new BookDto());
        when(bookService.findAll(any(Pageable.class))).thenReturn(bookDtoList);

        mockMvc.perform(get(BOOK_URL))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN", "USER"})
    @DisplayName("Return book by id with valid role")
    void getBookById_withUserRole_ReturnBook() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setId(BOOK_ID);
        bookDto.setTitle(BOOK_TITLE);

        when(bookService.findById(BOOK_ID)).thenReturn(bookDto);
        mockMvc.perform(get(BOOK_BY_ID_URL))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    @DisplayName("Delete book by id with valid role")
    void deleteBook_WithAdminRole_Success() throws Exception {
        doNothing().when(bookService).deleteById(BOOK_ID);
        mockMvc.perform(delete(BOOK_BY_ID_URL))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    @DisplayName("Delete book by id with invalid role")
    void deleteBook_WIthInValidRoleUser_unsuccess() throws Exception {

        mockMvc.perform(delete(BOOK_BY_ID_URL))
                .andExpect(status().isForbidden());
    }
}
