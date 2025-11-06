package com.farabi.taskmanager.mappers;

import com.farabi.taskmanager.dtos.CategoryDto;
import com.farabi.taskmanager.entities.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toEntity(CategoryDto categoryDto);
}
