package com.farabi.taskmanager.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private String stage;
    private Long categoryId;
    private LocalDate completionDate;
    private Long customerId;
    private Short priority;
    private Boolean isFlagged;
}
