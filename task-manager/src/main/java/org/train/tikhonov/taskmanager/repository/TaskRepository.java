package org.train.tikhonov.taskmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.train.tikhonov.taskmanager.entity.Task;

public interface TaskRepository extends MongoRepository<Task, String> {
}
