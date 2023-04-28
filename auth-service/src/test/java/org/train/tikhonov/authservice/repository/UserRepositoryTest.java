package org.train.tikhonov.authservice.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.train.tikhonov.authservice.entity.RoleEntity;
import org.train.tikhonov.authservice.entity.StatusEntity;
import org.train.tikhonov.authservice.entity.UserEntity;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class UserRepositoryTest {

    private final RoleRepository roleRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(RoleRepository roleRepository, StatusRepository statusRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.statusRepository = statusRepository;
        this.userRepository = userRepository;
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        statusRepository.deleteAll();
    }

    @Test
    void itShouldFindUserByEmail() {
        RoleEntity userRole = roleRepository.save(RoleEntity.builder().name("user").build());
        StatusEntity enabledStatus = statusRepository.save(StatusEntity.builder().name("enabled").build());
        String email = "aleksandr.tihonov.02@mail.ru";
        userRepository.save(
                UserEntity.
                        builder().
                        firstName("aleks").
                        lastName("tikhonov").
                        email("aleksandr.tihonov.02@mail.ru").
                        password("21471972").
                        status(enabledStatus).
                        roles(Set.of(userRole)).
                        build()
        );

        Optional<UserEntity> foundedUser = userRepository.findByEmail(email);

        assertThat(foundedUser.isPresent()).isTrue();
    }

    @Test
    void itShouldNotFindUserByEmail() {
        RoleEntity userRole = roleRepository.save(RoleEntity.builder().name("user").build());
        StatusEntity enabledStatus = statusRepository.save(StatusEntity.builder().name("enabled").build());
        String email = "some.email@some.some";
        userRepository.save(
                UserEntity.
                        builder().
                        firstName("aleks").
                        lastName("tikhonov").
                        email("aleksandr.tihonov.02@mail.ru").
                        password("21471972").
                        status(enabledStatus).
                        roles(Set.of(userRole)).
                        build()
        );

        Optional<UserEntity> foundedUser = userRepository.findByEmail(email);

        assertThat(foundedUser.isPresent()).isFalse();
    }
}