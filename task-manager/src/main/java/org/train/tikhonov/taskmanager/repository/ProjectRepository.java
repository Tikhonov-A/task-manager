package org.train.tikhonov.taskmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.train.tikhonov.taskmanager.entity.Project;

public interface ProjectRepository extends MongoRepository<Project, String> {
}
