package org.train.tikhonov.taskmanager.service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBRef;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.train.tikhonov.taskmanager.dto.TaskDto;
import org.train.tikhonov.taskmanager.entity.Task;
import org.train.tikhonov.taskmanager.entity.UserTasks;
import org.train.tikhonov.taskmanager.exception.TaskNotFoundException;
import org.train.tikhonov.taskmanager.exception.UserNotFoundException;
import org.train.tikhonov.taskmanager.mapper.TaskDtoMapper;
import org.train.tikhonov.taskmanager.repository.TaskRepository;
import org.train.tikhonov.taskmanager.repository.UserTaskRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
@RequiredArgsConstructor
@Transactional
public class UserTaskService {

    private final UserTaskRepository userTaskRepository;
    private final TaskRepository taskRepository;
    private final TaskDtoMapper taskDtoMapper;
    private final MongoTemplate mongoTemplate;

    public List<TaskDto> getAllUserTasks(Long userId) {
        UserTasks userTasks = userTaskRepository.findByUserId(userId).orElseThrow(() ->
                new UserNotFoundException("User not found")
        );
        return userTasks.getTasks().stream().map(taskDtoMapper).collect(Collectors.toList());
    }

    public void deleteUserTaskById(Long userId, String taskId) {
        taskRepository.findById(taskId).orElseThrow(() ->
                new TaskNotFoundException("Task not found")
        );
        taskRepository.deleteById(taskId);
        mongoTemplate.updateMulti(new Query().addCriteria(where("user_id").is(userId)),
                new Update().pull("tasks", new DBRef("tasks", taskId)), UserTasks.class);
    }

    public void updateUserTaskById(Long userId, String taskId, TaskDto taskRequest) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new TaskNotFoundException("Task not found")
        );
        task.setName(taskRequest.name());
        task.setDescription(taskRequest.description());
        task.setStatus(taskRequest.status());
        task.setCreatedAt(taskRequest.createdAt());
        task.setEndAt(taskRequest.endAt());
        task.setNotification(taskRequest.notification());
        taskRepository.save(task);
    }
}
