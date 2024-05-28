package dmytro.hadiuchko.springboot.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import dmytro.hadiuchko.springboot.config.MapperConfig;
import dmytro.hadiuchko.springboot.dto.category.request.CategoryRequestDto;
import dmytro.hadiuchko.springboot.dto.category.responce.CategoryResponseDto;
import dmytro.hadiuchko.springboot.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryResponseDto toDto(Category category);

    Category toModel(CategoryRequestDto dto);

    @Named("categoriesById")
    default Set<Category> categoriesByIds(Set<Long> categories) {
        return categories.stream()
                .map(Category::new)
                .collect(Collectors.toSet());
    }
}
