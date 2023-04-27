package org.train.tikhonov.authservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.train.tikhonov.authservice.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(String user);
}
