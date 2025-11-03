package com.farabi.taskmanager.mappers;

import com.farabi.taskmanager.dtos.TaskDto;
import com.farabi.taskmanager.entities.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toDto(Task task);
}
