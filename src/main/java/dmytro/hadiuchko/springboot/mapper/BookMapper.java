package dmytro.hadiuchko.springboot.mapper;

import java.util.stream.Collectors;
import dmytro.hadiuchko.springboot.config.MapperConfig;
import dmytro.hadiuchko.springboot.dto.book.request.CreateBookRequestDto;
import dmytro.hadiuchko.springboot.dto.book.responce.BookDto;
import dmytro.hadiuchko.springboot.dto.book.responce.BookDtoWithoutCategoryIds;
import dmytro.hadiuchko.springboot.entity.Book;
import dmytro.hadiuchko.springboot.entity.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class, uses = CategoryMapper.class)
public interface BookMapper {
    @Mapping(target = "id", source = "book.id")
    BookDto toDto(Book book);

    @Mapping(target = "categories", source = "categories", qualifiedByName = "categoriesById")
    Book toModel(CreateBookRequestDto requestDto);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        bookDto.setCategoryIds(book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet()));
    }

    @Named("bookFromId")
    default Book bookFromId(Long id) {
        Book book = new Book();

        book.setId(id);
        return book;
    }

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);
}
