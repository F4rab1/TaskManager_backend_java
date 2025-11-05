package com.farabi.taskmanager.mappers;

import com.farabi.taskmanager.dtos.TaskDto;
import com.farabi.taskmanager.dtos.TaskRequestDto;
import com.farabi.taskmanager.entities.Task;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toDto(Task task);

    Task toEntity(TaskRequestDto taskRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTask(TaskRequestDto taskRequestDto, @MappingTarget Task task);
}
