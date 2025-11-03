package com.farabi.taskmanager.repositories;

import com.farabi.taskmanager.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
