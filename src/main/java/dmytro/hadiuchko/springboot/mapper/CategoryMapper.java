package dmytro.hadiuchko.springboot.mapper;

import dmytro.hadiuchko.springboot.config.MapperConfig;
import dmytro.hadiuchko.springboot.dto.category.request.CategoryRequestDto;
import dmytro.hadiuchko.springboot.dto.category.responce.CategoryResponseDto;
import dmytro.hadiuchko.springboot.entity.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryResponseDto toDto(Category category);

    Category toModel(CategoryRequestDto dto);
}
