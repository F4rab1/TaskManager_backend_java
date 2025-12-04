package com.farabi.taskmanager.controllers;

import com.farabi.taskmanager.dtos.TaskDto;
import com.farabi.taskmanager.dtos.TaskRequestDto;
import com.farabi.taskmanager.entities.TaskStage;
import com.farabi.taskmanager.mappers.TaskMapper;
import com.farabi.taskmanager.repositories.CategoryRepository;
import com.farabi.taskmanager.repositories.TaskRepository;
import com.farabi.taskmanager.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
@Tag(name = "Tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    @Operation(summary = "Get all tasks.")
    public ResponseEntity<List<TaskDto>> getAllTasks(
            @RequestParam(required = false) String stage,
            @RequestParam(required = false) Short priority,
            @RequestParam(required = false) Boolean is_flagged
    ) {
        return ResponseEntity.ok(taskService.getAllTasks(stage, priority, is_flagged));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(
            @Valid @RequestBody TaskRequestDto taskRequestDto,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        var taskDto = taskService.createTask(taskRequestDto);
        var uri = uriComponentsBuilder.path("/tasks/{id}").buildAndExpand(taskDto.getId()).toUri();

        return ResponseEntity.created(uri).body(taskDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(
            @PathVariable(name = "id") Long id,
            @RequestBody TaskRequestDto taskRequestDto
    ) {
        return ResponseEntity.ok(taskService.updateTask(id, taskRequestDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException exception
    ) {
        var errors = new HashMap<String, String>();

        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(error.getField(), error.getDefaultMessage())
                );

        return ResponseEntity.badRequest().body(errors);
    }
}
