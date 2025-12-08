package com.farabi.taskmanager.categories;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryDto {
    private Long id;

    @NotBlank(message = "Name must be provided")
    private String name;
}
