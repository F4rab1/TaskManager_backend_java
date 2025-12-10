package com.farabi.taskmanager.categories;

import com.farabi.taskmanager.auth.AuthService;
import com.farabi.taskmanager.tasks.TaskDto;
import com.farabi.taskmanager.tasks.TaskMapper;
import com.farabi.taskmanager.tasks.TaskRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final AuthService authService;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();
    }

    public CategoryDto getCategoryById(Long id) {
        var category = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Category not found: " + id));

        return categoryMapper.toDto(category);
    }

    public List<TaskDto> getTasksByCategoryId(Long categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryId));

        long userId = authService.getCurrentUserId();

        var tasks = taskRepository.findAllByCategoryIdAndCustomerId(categoryId, userId);

        return tasks.stream()
                .map(taskMapper::toDto)
                .toList();
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
