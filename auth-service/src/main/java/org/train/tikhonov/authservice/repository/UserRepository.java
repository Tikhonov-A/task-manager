package org.train.tikhonov.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.train.tikhonov.authservice.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
