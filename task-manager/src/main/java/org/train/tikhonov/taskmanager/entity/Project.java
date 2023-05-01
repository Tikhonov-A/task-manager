package org.train.tikhonov.taskmanager.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Project {

    @Id
    private String id;
    private String name;
    private String projectDescription;
    private LocalDateTime createdAt;
    private List<Task> tasks;
}
