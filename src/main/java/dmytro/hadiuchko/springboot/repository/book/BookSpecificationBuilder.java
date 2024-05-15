package dmytro.hadiuchko.springboot.repository.book;

import dmytro.hadiuchko.springboot.dto.book.request.BookSearchParametersDto;
import dmytro.hadiuchko.springboot.entity.Book;
import dmytro.hadiuchko.springboot.repository.SpecificationBuilder;
import dmytro.hadiuchko.springboot.repository.SpecificationProviderManager;
import dmytro.hadiuchko.springboot.repository.book.spec.AuthorSpecificationProvider;
import dmytro.hadiuchko.springboot.repository.book.spec.CategorySpecificationProvider;
import dmytro.hadiuchko.springboot.repository.book.spec.IsbnSpecificationProvider;
import dmytro.hadiuchko.springboot.repository.book.spec.TitleSpecificationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookSpecificationBuilder implements SpecificationBuilder<Book> {

    private final SpecificationProviderManager<Book> bookSpecificationProviderManager;

    @Override
    public Specification<Book> build(BookSearchParametersDto searchParametersDto) {
        Specification<Book> spec = Specification.where(null);
        if (isNotNullOrEmpty(searchParametersDto.title())) {
            spec = buildSpecification(TitleSpecificationProvider.TITLE,
                    searchParametersDto.title());
        }
        if (isNotNullOrEmpty(searchParametersDto.author())) {
            spec = buildSpecification(AuthorSpecificationProvider.AUTHOR,
                    searchParametersDto.author());
        }
        if (isNotNullOrEmpty(searchParametersDto.isbn())) {
            spec = buildSpecification(IsbnSpecificationProvider.ISBN,
                    searchParametersDto.isbn());
        }
        if (isNotNullOrEmpty(searchParametersDto.category())) {
            spec = buildSpecification(CategorySpecificationProvider.CATEGORY,
                    searchParametersDto.isbn());
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
