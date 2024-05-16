package dmytro.hadiuchko.springboot.repository.book.spec;

import dmytro.hadiuchko.springboot.entity.Book;
import dmytro.hadiuchko.springboot.repository.book.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthorSpecificationProvider implements SpecificationProvider<Book> {
    public static final String AUTHOR = "author";

    @Override
    public String getKey() {
        return AUTHOR;
    }

    @Override
    public Specification<Book> getSpecification(String param) {
        return (root, query, criteriaBuilder) -> root.get(AUTHOR).in(param);
    }
}
