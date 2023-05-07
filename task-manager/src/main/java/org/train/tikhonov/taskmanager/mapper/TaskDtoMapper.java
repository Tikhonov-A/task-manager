package org.train.tikhonov.taskmanager.mapper;

import org.springframework.stereotype.Component;
import org.train.tikhonov.taskmanager.dto.TaskDto;
import org.train.tikhonov.taskmanager.entity.Task;

import java.util.function.Function;

@Component
public class TaskDtoMapper implements Function<Task, TaskDto> {

    @Override
    public TaskDto apply(Task task) {
        return new TaskDto(
                task.getName(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getEndAt(),
                task.getNotification()
        );
    }
}
