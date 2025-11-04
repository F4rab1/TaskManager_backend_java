package com.farabi.taskmanager.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequestDto {
    private String title;
    private String description;
    private Long categoryId;
    private LocalDate completionDate;
    private Short priority;
    private Boolean isFlagged;
}
