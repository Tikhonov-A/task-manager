package org.train.tikhonov.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.train.tikhonov.authservice.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserEntityByUsername(String username);
}
