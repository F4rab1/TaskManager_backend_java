package com.farabi.taskmanager;

import com.farabi.taskmanager.dtos.TaskDto;
import com.farabi.taskmanager.entities.Category;
import com.farabi.taskmanager.entities.Task;
import com.farabi.taskmanager.repositories.CategoryRepository;
import com.farabi.taskmanager.repositories.TaskRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class TaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
//        var context = SpringApplication.run(TaskManagerApplication.class, args);
//        var taskRepo = context.getBean(TaskRepository.class);
//        var categoryRepo = context.getBean(CategoryRepository.class);
//
//        var task = taskRepo.findById(6L).orElseThrow();
//        var category = Category.builder()
//                .name("Math")
//                .build();
//        categoryRepo.save(category);

//        categoryRepo.findAll().forEach(category1 -> System.out.println(category1.toString()));

//        var task = Task.builder()
//                .title("Task Manager")
//                .description("Task Manager")
//                .priority((short) 2)
//                .completionDate(LocalDate.now().plusDays(1))
//                .build();
//
//        taskRepo.save(task);
//        System.out.println(task);
    }

}
