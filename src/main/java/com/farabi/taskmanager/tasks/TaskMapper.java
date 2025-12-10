package com.farabi.taskmanager.tasks;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(source = "category.id", target = "categoryId")
    TaskDto toDto(Task task);

    @Mapping(source = "categoryId", target = "category.id")
    Task toEntity(TaskRequestDto taskRequestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateTask(TaskRequestDto taskRequestDto, @MappingTarget Task task);
}
