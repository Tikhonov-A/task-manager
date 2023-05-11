package org.train.tikhonov.taskmanager.service;

import com.mongodb.DBRef;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.train.tikhonov.taskmanager.dto.TaskRequest;
import org.train.tikhonov.taskmanager.dto.TaskResponse;
import org.train.tikhonov.taskmanager.entity.Task;
import org.train.tikhonov.taskmanager.entity.UserTasks;
import org.train.tikhonov.taskmanager.exception.TaskNotFoundException;
import org.train.tikhonov.taskmanager.mapper.TaskResponseMapper;
import org.train.tikhonov.taskmanager.repository.TaskRepository;
import org.train.tikhonov.taskmanager.repository.UserTaskRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
@RequiredArgsConstructor
@Transactional
public class UserTaskService {

    private final UserTaskRepository userTaskRepository;
    private final TaskRepository taskRepository;
    private final TaskResponseMapper taskResponseMapper;
    private final MongoTemplate mongoTemplate;

    public List<TaskResponse> getAllUserTasks(UUID userId) {
        Optional<UserTasks> userTasksOptional = userTaskRepository.findByUserId(userId);
        if (userTasksOptional.isEmpty()) {
            return new ArrayList<>();
        }
        UserTasks userTasks = userTasksOptional.get();
        return userTasks.getTasks().stream().map(taskResponseMapper).collect(Collectors.toList());
    }

    public void deleteUserTaskById(UUID userId, String taskId) {
        taskRepository.findById(taskId).orElseThrow(() ->
                new TaskNotFoundException("Task not found")
        );
        taskRepository.deleteById(taskId);
        mongoTemplate.updateMulti(new Query().addCriteria(where("user_id").is(userId)),
                new Update().pull("tasks", new DBRef("tasks", taskId)), UserTasks.class);
    }

    public void updateUserTaskById(UUID userId, String taskId, TaskRequest taskRequest) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new TaskNotFoundException("Task not found")
        );
        task.setName(taskRequest.name());
        task.setDescription(taskRequest.description());
        task.setStatus(taskRequest.status());
        task.setCreatedAt(LocalDateTime.now());
        task.setEndAt(taskRequest.endAt());
        task.setNotification(taskRequest.notification());
        taskRepository.save(task);
    }

    public void addUserTask(UUID userId, TaskRequest task) {
        Optional<UserTasks> userTasks = userTaskRepository.findByUserId(userId);
        Task savedTask = taskRepository.save(
                new Task(
                        task.name(),
                        task.description(),
                        task.status(),
                        LocalDateTime.now(),
                        task.endAt(),
                        task.notification()
                )
        );
        if (userTasks.isEmpty()) {
            userTaskRepository.save(new UserTasks(
                userId, List.of(savedTask)
            ));
        } else {
            userTasks.get().getTasks().add(savedTask);
            userTaskRepository.save(userTasks.get());
        }
    }
}
