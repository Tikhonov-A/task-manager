package org.train.tikhonov.taskmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.train.tikhonov.taskmanager.entity.UserProjects;

public interface UserProjectsRepository extends MongoRepository<UserProjects, String> {
}
