package com.farabi.taskmanager.services;

import com.farabi.taskmanager.dtos.TaskDto;
import com.farabi.taskmanager.dtos.TaskRequestDto;
import com.farabi.taskmanager.entities.TaskStage;
import com.farabi.taskmanager.mappers.TaskMapper;
import com.farabi.taskmanager.repositories.CategoryRepository;
import com.farabi.taskmanager.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final CategoryRepository categoryRepository;
    private final AuthService authService;

    public List<TaskDto> getAllTasks(String stage, Short priority, Boolean is_flagged) {
        if (stage != null &&
                !stage.equalsIgnoreCase("in_progress") &&
                !stage.equalsIgnoreCase("completed")
        ) {
            throw new IllegalArgumentException("Invalid stage: " + stage);
        }

        if (priority != null && (priority < 1 || priority > 5)) {
            throw new IllegalArgumentException("Priority must be between 1 and 5");
        }

        Long userId = authService.getCurrentUserId();

        return taskRepository.findAllByCustomerId(userId)
                .stream()
                .filter(task -> stage == null || stage.equalsIgnoreCase(String.valueOf(task.getStage())))
                .filter(task -> priority == null ||
                        (task.getPriority() != null && task.getPriority().equals(priority)))
                .filter(task -> is_flagged == null ||
                        (task.getIsFlagged() != null && task.getIsFlagged() == is_flagged))
                .map(taskMapper::toDto)
                .toList();

    }

    public TaskDto getTaskById(Long id) {
        var task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found: " + id));

        return taskMapper.toDto(task);
    }

    public TaskDto createTask(TaskRequestDto dto) {
        if (dto.getPriority() == null) {
            dto.setPriority((short) 1);
        }

        if (dto.getIsFlagged() == null) {
            dto.setIsFlagged(false);
        }

        if (dto.getCompletionDate() != null && dto.getCompletionDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Completion date cannot be in the past");
        }

        var category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> new IllegalArgumentException("Invalid category id"));

        var task = taskMapper.toEntity(dto);
        task.setStage(dto.getStage() == null ? TaskStage.in_progress : TaskStage.valueOf(dto.getStage()));

        task.setCustomerId(authService.getCurrentUserId());
        task.setCategory(category);

        taskRepository.save(task);

        return taskMapper.toDto(task);
    }

    public TaskDto updateTask(Long id, TaskRequestDto dto) {
        var task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found: " + id));

        taskMapper.updateTask(dto, task);
        taskRepository.save(task);

        return taskMapper.toDto(task);
    }

    public void deleteTask(Long id) {
        var task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found: " + id));

        taskRepository.delete(task);
    }
}
