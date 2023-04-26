package org.train.tikhonov.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.train.tikhonov.authservice.entity.StatusEntity;

public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
    StatusEntity findByName(String name);
}
