package dmytro.hadiuchko.springboot.repository;

import dmytro.hadiuchko.springboot.dto.request.BookSearchParametersDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
    Specification<T> build(BookSearchParametersDto searchParametersDto);
}
