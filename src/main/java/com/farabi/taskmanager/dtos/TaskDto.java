package com.farabi.taskmanager.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@ToString
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
