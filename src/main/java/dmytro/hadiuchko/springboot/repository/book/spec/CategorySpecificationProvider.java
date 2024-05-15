package dmytro.hadiuchko.springboot.repository.book.spec;

import dmytro.hadiuchko.springboot.entity.Category;
import dmytro.hadiuchko.springboot.repository.book.SpecificationProvider;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CategorySpecificationProvider implements SpecificationProvider<Category> {
    private static final String CATEGORY = "category";

    @Override
    public String getKey() {
        return CATEGORY;
    }

    @Override
    public Specification<Category> getSpecification(String param) {
        return ((root, query, criteriaBuilder) -> root.get(CATEGORY).in(param));
    }
}
