package org.train.tikhonov.authservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.train.tikhonov.authservice.entity.RoleEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class RoleRepositoryTest {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleRepositoryTest(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Test
    void itShouldFindRoleByName() {
        roleRepository.save(RoleEntity.builder().name("admin").build());
        RoleEntity role = roleRepository.findByName("admin");
        assertThat(role).isNotNull();
    }

    @Test
    void itShouldNotFindRoleByName() {
        RoleEntity role = roleRepository.findByName("admin");
        assertThat(role).isNull();
    }
}