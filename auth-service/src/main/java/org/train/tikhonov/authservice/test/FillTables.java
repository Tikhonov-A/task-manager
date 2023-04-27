package org.train.tikhonov.authservice.test;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.train.tikhonov.authservice.entity.RoleEntity;
import org.train.tikhonov.authservice.entity.StatusEntity;
import org.train.tikhonov.authservice.entity.UserEntity;
import org.train.tikhonov.authservice.repository.RoleRepository;
import org.train.tikhonov.authservice.repository.StatusRepository;
import org.train.tikhonov.authservice.repository.UserRepository;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class FillTables implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        RoleEntity admin = roleRepository.save(RoleEntity.builder().name("admin").build());
        RoleEntity user = roleRepository.save(RoleEntity.builder().name("user").build());

        statusRepository.save(StatusEntity.builder().name("locked").build());
        statusRepository.save(StatusEntity.builder().name("unverified").build());
        StatusEntity enabled = statusRepository.save(StatusEntity.builder().name("enabled").build());
        statusRepository.save(StatusEntity.builder().name("deleted").build());
        userRepository.save(
                UserEntity.
                        builder().
                        firstName("aleks").
                        lastName("tikhonov").
                        email("aleksandr.tihonov.02@mail.ru").
                        password(passwordEncoder.encode("21471972")).
                        status(enabled).
                        roles(Set.of(admin, user)).
                        build()
        );
    }
}
