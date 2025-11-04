package com.farabi.taskmanager.controllers;

import com.farabi.taskmanager.dtos.TaskDto;
import com.farabi.taskmanager.mappers.TaskMapper;
import com.farabi.taskmanager.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks(
            @RequestParam(required = false) String stage,
            @RequestParam(required = false) Short priority,
            @RequestParam(required = false) Boolean is_flagged
    ) {
        if (stage != null &&
            !stage.equalsIgnoreCase("in_progress") &&
            !stage.equalsIgnoreCase("completed")
        ) {
            return ResponseEntity.badRequest().build();
        }

        if (priority != null && (priority < 1 || priority > 5)) {
            return ResponseEntity.badRequest().build();
        }

        var tasks = taskRepository.findAll()
                .stream()
                .filter(task -> stage == null || (task.getStage() != null && task.getStage().equalsIgnoreCase(stage)))
                .filter(task -> priority == null || (task.getPriority() != null && task.getPriority().equals(priority)))
                .filter(task -> is_flagged == null || (task.getIsFlagged() != null && task.getIsFlagged() == is_flagged))
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
