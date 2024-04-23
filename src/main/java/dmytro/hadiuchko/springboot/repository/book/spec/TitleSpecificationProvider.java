package dmytro.hadiuchko.springboot.repository.book.spec;

import dmytro.hadiuchko.springboot.entity.Book;
import dmytro.hadiuchko.springboot.repository.book.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TitleSpecificationProvider implements SpecificationProvider<Book> {
    @Override
    public String getKey() {
        return "title";
    }

    public Specification<Book> getSpecification(String param) {
        return (root, query, criteriaBuilder) -> root.get("title").in(param);
    }
}
