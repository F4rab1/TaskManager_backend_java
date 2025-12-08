package com.farabi.taskmanager.categories;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(@NotBlank(message = "Name must be provided") String name);
}

