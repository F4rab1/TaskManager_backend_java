package com.farabi.taskmanager.controllers;

import com.farabi.taskmanager.dtos.TaskDto;
import com.farabi.taskmanager.mappers.TaskMapper;
import com.farabi.taskmanager.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        var tasks = taskRepository.findAll()
                .stream()
                .map(taskMapper::toDto)
                .toList();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        var task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(taskMapper.toDto(task));
    }
}
