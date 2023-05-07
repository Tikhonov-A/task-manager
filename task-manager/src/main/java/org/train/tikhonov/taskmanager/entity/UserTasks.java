package org.train.tikhonov.taskmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user_tasks")
@ToString
public class UserTasks {

    @Id
    private String id;

    @Indexed(unique = true)
    @Field("user_id")
    private Long userId;

    @DBRef
    private List<Task> tasks;

    public UserTasks(Long userId, List<Task> tasks) {
        this.userId = userId;
        this.tasks = tasks;
    }

}
