package org.train.tikhonov.authservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.train.tikhonov.authservice.entity.RoleEntity;

import java.util.Set;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Set<RoleEntity> findAllByName(String name);
}
