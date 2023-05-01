package org.train.tikhonov.taskmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.train.tikhonov.taskmanager.entity.TaskStatus;

public interface TaskStatusRepository extends MongoRepository<TaskStatus, String> {
}
