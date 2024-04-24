package dmytro.hadiuchko.springboot.repository.book.spec;

import dmytro.hadiuchko.springboot.entity.Book;
import dmytro.hadiuchko.springboot.repository.book.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class IsbnSpecificationProvider implements SpecificationProvider<Book> {
    private static final String ISBN = "isbn";

    @Override
    public String getKey() {
        return ISBN;
    }

    @Override
    public Specification<Book> getSpecification(String param) {
        return (root, query, criteriaBuilder) -> root.get(ISBN).in(param);
    }
}
