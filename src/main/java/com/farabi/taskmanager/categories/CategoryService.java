package com.farabi.taskmanager.categories;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();
    }

    public CategoryDto getCategoryById(Long id) {
        var category = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));

        return categoryMapper.toDto(category);
    }


    public CategoryDto createCategory(@Valid CategoryDto categoryDto) {
        if (categoryRepository.existsByName(categoryDto.getName())) {
            throw new IllegalArgumentException("Category name already exists: " + categoryDto.getName());
        }

        var category = categoryMapper.toEntity(categoryDto);
        categoryRepository.save(category);

        categoryDto.setId(category.getId());

        return categoryDto;
    }

    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        var category = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));

        category.setName(categoryDto.getName());
        categoryRepository.save(category);

        return categoryMapper.toDto(category);
    }

    public void deleteCategoryById(Long id) {
        var category = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));

        categoryRepository.delete(category);
    }
}
