package org.train.tikhonov.taskmanager.mapper;

import org.springframework.stereotype.Component;
import org.train.tikhonov.taskmanager.dto.TaskRequest;
import org.train.tikhonov.taskmanager.dto.TaskResponse;
import org.train.tikhonov.taskmanager.entity.Task;

import java.util.function.Function;

@Component
public class TaskResponseMapper implements Function<Task, TaskResponse> {

    @Override
    public TaskResponse apply(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getName(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getEndAt(),
                task.getNotification()
        );
    }
}
