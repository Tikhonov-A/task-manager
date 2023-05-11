package org.train.tikhonov.taskmanager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.train.tikhonov.taskmanager.entity.Task;
import org.train.tikhonov.taskmanager.entity.TaskStatus;
import org.train.tikhonov.taskmanager.entity.UserTasks;
import org.train.tikhonov.taskmanager.repository.TaskRepository;
import org.train.tikhonov.taskmanager.repository.UserTaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
@EnableDiscoveryClient
public class TaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }

    @Bean
    public CommandLineRunner test(
            UserTaskRepository userTaskRepository,
            TaskRepository taskRepository
    ) {
        return args -> {
//            Task task1 = Task.builder().
//                    name("task1").
//                    description("Some task").
//                    status(TaskStatus.PLANNED).
//                    createdAt(LocalDateTime.now()).
//                    endAt(LocalDateTime.now()).
//                    notification(LocalDateTime.now()).
//                    build();
//            Task task2 = Task.builder().
//                    name("task2").
//                    status(TaskStatus.DOING).
//                    createdAt(LocalDateTime.now()).
//                    build();
//            task1 = taskRepository.save(task1);
//            task2 = taskRepository.save(task2);
//            userTaskRepository.save(new UserTasks(UUID.randomUUID(), List.of(task1, task2)));
        };
    }

}
