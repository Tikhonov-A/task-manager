package org.train.tikhonov.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "tasks")
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Task {

    @Id
    private String id;
    private String name;
    private String description;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime endAt;
    private LocalDateTime notification;
}
