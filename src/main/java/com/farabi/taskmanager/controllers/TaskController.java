package com.farabi.taskmanager.controllers;

import com.farabi.taskmanager.dtos.TaskDto;
import com.farabi.taskmanager.dtos.TaskRequestDto;
import com.farabi.taskmanager.mappers.TaskMapper;
import com.farabi.taskmanager.repositories.CategoryRepository;
import com.farabi.taskmanager.repositories.TaskRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {
    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
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

    @PostMapping
    public ResponseEntity<TaskDto> createTask(
            @Valid @RequestBody TaskRequestDto taskRequestDto,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        if (taskRequestDto.getPriority() == null) {
            taskRequestDto.setPriority((short) 1);
        }

        if (taskRequestDto.getCompletionDate() != null && taskRequestDto.getCompletionDate().isBefore(LocalDate.now())) {
            return ResponseEntity.badRequest().build();
        }

        if (taskRequestDto.getIsFlagged() == null) {
            taskRequestDto.setIsFlagged(false);
        }

        var category = categoryRepository.findById(taskRequestDto.getCategoryId()).orElse(null);
        var task = taskMapper.toEntity(taskRequestDto);
        task.setCategory(category);
        taskRepository.save(task);

        var taskDto = taskMapper.toDto(task);
        var uri = uriComponentsBuilder.path("/tasks/{id}").buildAndExpand(taskDto.getId()).toUri();
        return ResponseEntity.created(uri).body(taskDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(
            @PathVariable(name = "id") Long id,
            @RequestBody TaskRequestDto taskRequestDto
    ) {
        var task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        taskMapper.updateTask(taskRequestDto, task);
        taskRepository.save(task);

        return ResponseEntity.ok(taskMapper.toDto(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        var task = taskRepository.findById(id).orElse(null);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }

        taskRepository.delete(task);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(
            MethodArgumentNotValidException exception
    ) {
        var errors = new HashMap<String, String>();

        exception.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }
}
