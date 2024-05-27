package dmytro.hadiuchko.springboot.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dmytro.hadiuchko.springboot.dto.category.request.CategoryRequestDto;
import dmytro.hadiuchko.springboot.dto.category.responce.CategoryResponseDto;
import dmytro.hadiuchko.springboot.entity.Category;
import dmytro.hadiuchko.springboot.mapper.CategoryMapper;
import dmytro.hadiuchko.springboot.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    private static final String CATEGORY_DESCRIPTION = "interesting book";
    private static final String CATEGORY_NAME = "Fiction";
    private static final String ANOTHER_CATEGORY_DESCRIPTION = "another description";
    private static final String UPDATED_NAME = "Updated name";
    private static final String UPDATED_DESCRIPTION = "Updated description";
    private static final Long CATEGORY_ID = 1L;
    private static final Long SECOND_CATEGORY_ID = 2L;
    @Mock
    private CategoryMapper categoryMapper;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Save a new category")
    void save_validRequestDto_success() {
        CategoryRequestDto requestDto = new CategoryRequestDto(CATEGORY_NAME,
                CATEGORY_DESCRIPTION);
        CategoryResponseDto responseDto = new CategoryResponseDto();
        responseDto.setId(CATEGORY_ID);
        responseDto.setName(requestDto.name());
        responseDto.setDescription(requestDto.description());

        Category category = new Category();
        when(categoryMapper.toModel(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(responseDto);

        CategoryResponseDto result = categoryService.save(requestDto);
        assertEquals(responseDto, result);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Find all categories")
    void findAll_validData_success() {
        List<Category> categories = List.of(new Category(), new Category(), new Category());
        Page<Category> categoryPage = new PageImpl<>(categories);
        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categoryPage);
        when(categoryMapper.toDto(any())).thenReturn(new CategoryResponseDto());

        List<CategoryResponseDto> result = categoryService.findAll(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(3, result.size());
    }

    @Test
    @DisplayName("Find category by id")
    void findById_validData_success() {
        CategoryResponseDto responseDto = new CategoryResponseDto();
        responseDto.setId(CATEGORY_ID);
        responseDto.setName(CATEGORY_NAME);

        CategoryResponseDto secondResponse = new CategoryResponseDto();
        secondResponse.setId(SECOND_CATEGORY_ID);
        responseDto.setName(ANOTHER_CATEGORY_DESCRIPTION);

        Category category = new Category();
        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(responseDto);

        CategoryResponseDto result = categoryService.findById(CATEGORY_ID);

        assertNotNull(result);
        assertEquals(responseDto.getName(), result.getName());
        assertNotEquals(secondResponse.getName(), result.getName());
    }

    @Test
    @DisplayName("Update category by id")
    void updateById_validData_success() {
        Category category = new Category();
        category.setId(CATEGORY_ID);
        category.setDescription(CATEGORY_DESCRIPTION);
        category.setName(CATEGORY_NAME);

        CategoryRequestDto requestDto = new CategoryRequestDto(UPDATED_NAME, UPDATED_DESCRIPTION);

        Category updatedCategory = new Category();
        updatedCategory.setId(CATEGORY_ID);
        updatedCategory.setDescription(requestDto.description());
        updatedCategory.setName(requestDto.name());

        CategoryResponseDto responseDto = new CategoryResponseDto();
        responseDto.setId(CATEGORY_ID);
        responseDto.setDescription(requestDto.description());
        responseDto.setName(requestDto.name());

        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);
        when(categoryMapper.toDto(any(Category.class))).thenReturn(responseDto);

        CategoryResponseDto result = categoryService.update(CATEGORY_ID, requestDto);
        assertNotNull(result);
        EqualsBuilder.reflectionEquals(responseDto, result, "id");
    }

    @Test
    @DisplayName("Delete category by id")
    void deleteById_validData_success() {
        Category category = new Category();
        category.setId(CATEGORY_ID);
        category.setName(CATEGORY_NAME);
        category.setDescription(CATEGORY_DESCRIPTION);
        categoryRepository.save(category);

        when(categoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(category));

        categoryService.deleteById(CATEGORY_ID);
        verify(categoryRepository, times(1)).delete(category);
    }
}
