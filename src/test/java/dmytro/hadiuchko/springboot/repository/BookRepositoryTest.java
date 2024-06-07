package dmytro.hadiuchko.springboot.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dmytro.hadiuchko.springboot.entity.Book;
import dmytro.hadiuchko.springboot.entity.Category;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    private static final String TEST_CATEGORY = "Test Category";
    private static final String BOOK_1 = "Book 1";
    private static final String AUTHOR_1 = "Author 1";
    private static final String FIRST_ISBN = "ISBN111";
    private static final BigDecimal PRICE_FOR_FIRST_BOOK = BigDecimal.valueOf(20.0);
    private static final String DESCRIPTION_FOR_FIRST_BOOK = "Description for Book 1";
    private static final String COVER_IMAGE_FOR_FIRST_BOOK = "cover1.jpg";
    private static final String BOOK_2 = "Book 2";
    private static final String AUTHOR_2 = "Author 2";
    private static final String SECOND_BOOK_ISBN = "ISBN222";
    private static final BigDecimal PRICE_FOR_SECOND_BOOK = BigDecimal.valueOf(30.0);
    private static final String DESCRIPTION_FOR_BOOK_2 = "Description for Book 2";
    private static final String COVER_IMAGE_FOR_SECOND_BOOK = "cover2.jpg";

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("find All books by categories id")
    void findAllByCategoriesId_validValues_returnsBook() {
        Category category = new Category();
        category.setName(TEST_CATEGORY);
        entityManager.persist(category);

        Book book1 = new Book();
        book1.setTitle(BOOK_1);
        book1.setAuthor(AUTHOR_1);
        book1.setIsbn(FIRST_ISBN);
        book1.setPrice(PRICE_FOR_FIRST_BOOK);
        book1.setDescription(DESCRIPTION_FOR_FIRST_BOOK);
        book1.setCoverImage(COVER_IMAGE_FOR_FIRST_BOOK);
        book1.setCategories(Collections.singleton(category));
        entityManager.persist(book1);

        Book book2 = new Book();
        book2.setTitle(BOOK_2);
        book2.setAuthor(AUTHOR_2);
        book2.setIsbn(SECOND_BOOK_ISBN);
        book2.setPrice(PRICE_FOR_SECOND_BOOK);
        book2.setDescription(DESCRIPTION_FOR_BOOK_2);
        book2.setCoverImage(COVER_IMAGE_FOR_SECOND_BOOK);
        book2.setCategories(Collections.singleton(category));
        entityManager.persist(book2);

        entityManager.flush();

        List<Book> books = bookRepository.findAllByCategoriesId(category.getId());

        assertEquals(2, books.size());
        assertTrue(books.stream().allMatch(b -> b.getCategories().contains(category)));
    }
}
