package dmytro.hadiuchko.springboot.repository.book;

import dmytro.hadiuchko.springboot.dto.request.BookSearchParametersDto;
import dmytro.hadiuchko.springboot.entity.Book;
import dmytro.hadiuchko.springboot.repository.SpecificationBuilder;
import dmytro.hadiuchko.springboot.repository.SpecificationProviderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {
    private static final String AUTHOR_KEY = "author";
    private static final String TITLE_KEY = "title";
    private static final String ISBN_KEY = "isbn";

    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParametersDto) {
        Specification<Book> spec = Specification.where(null);
        if (isNotNullOrEmpty(searchParametersDto.title())) {
            spec = buildSpecification(TITLE_KEY, searchParametersDto.title());
        }
        if (isNotNullOrEmpty(searchParametersDto.author())) {
            spec = buildSpecification(AUTHOR_KEY, searchParametersDto.author());
        }
        if (isNotNullOrEmpty(searchParametersDto.isbn())) {
            spec = buildSpecification(ISBN_KEY, searchParametersDto.isbn());
        }
        return spec;
    }

    private Specification<Book> buildSpecification(String key, String value) {
        return bookSpecificationProviderManager.getSpecificationProvider(key)
                .getSpecification(value);
    }

    private boolean isNotNullOrEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
