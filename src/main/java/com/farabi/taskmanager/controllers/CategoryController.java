package com.farabi.taskmanager.controllers;

import com.farabi.taskmanager.dtos.CategoryDto;
import com.farabi.taskmanager.mappers.CategoryMapper;
import com.farabi.taskmanager.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        var entities = categoryRepository.findAll();
        entities.forEach(e -> System.out.println("Entity: id=" + e.getId() + ", name=" + e.getName()));

        var categories = entities.stream()
                .map(categoryMapper::toDto)
                .toList();

        return ResponseEntity.ok(categories);
    }
}
