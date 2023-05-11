package org.train.tikhonov.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.train.tikhonov.authservice.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
}
