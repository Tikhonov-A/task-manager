package org.train.tikhonov.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.train.tikhonov.taskmanager.entity.TaskStatus;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TaskDto(
        String name,
        String description,
        TaskStatus status,
        LocalDateTime createdAt,
        LocalDateTime endAt,
        LocalDateTime notification
        )
{ }
