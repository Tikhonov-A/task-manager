package org.train.tikhonov.taskmanager.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.train.tikhonov.taskmanager.entity.Task;
import org.train.tikhonov.taskmanager.entity.UserTasks;

import java.util.List;
import java.util.Optional;

public interface UserTaskRepository extends MongoRepository<UserTasks, String> {


    Optional<UserTasks> findByUserId(Long userId);

    @Query(value = "{$pull: {'userId': ?0, tasks.id : ?1}}")
    void deleteTaskByTaskId(Long userId, String taskId);
}
