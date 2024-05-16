package dmytro.hadiuchko.springboot.service.impl;

import dmytro.hadiuchko.springboot.dto.category.request.CategoryRequestDto;
import dmytro.hadiuchko.springboot.dto.category.responce.CategoryResponseDto;
import dmytro.hadiuchko.springboot.entity.Category;
import dmytro.hadiuchko.springboot.exception.EntityNotFoundException;
import dmytro.hadiuchko.springboot.mapper.CategoryMapper;
import dmytro.hadiuchko.springboot.repository.CategoryRepository;
import dmytro.hadiuchko.springboot.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponseDto> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).stream()
               .map(categoryMapper::toDto)
               .toList();
    }

    @Override
    public CategoryResponseDto findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find category by id: " + id));
    }

    @Override
    public CategoryResponseDto save(CategoryRequestDto categoryRequestDto) {
        Category category = categoryMapper.toModel(categoryRequestDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryResponseDto update(Long id, CategoryRequestDto categoryRequestDto) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find category by id: " + id));
        category.setDescription(categoryRequestDto.description());
        category.setDescription(categoryRequestDto.name());
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete, () -> {
            throw new EntityNotFoundException("Can't find category by id: " + id);
        });
    }
}
