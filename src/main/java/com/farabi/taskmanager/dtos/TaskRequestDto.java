package com.farabi.taskmanager.dtos;

import com.farabi.taskmanager.validations.Lowercase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequestDto {
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must be less than 255 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description must be less than 255 characters")
    private String description;

    @Lowercase(message = "Stage must be in lowercase")
    @Pattern(regexp = "^(in_progress|completed)$", message = "Stage must be either 'in_progress' or 'completed'")
    private String stage;

    private Long categoryId;

    @NotNull
    private LocalDate completionDate;

    private Short priority;

    private Boolean isFlagged;
}
