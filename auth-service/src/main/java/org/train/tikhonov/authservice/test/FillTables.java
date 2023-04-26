package org.train.tikhonov.authservice.test;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.train.tikhonov.authservice.entity.RoleEntity;
import org.train.tikhonov.authservice.entity.StatusEntity;
import org.train.tikhonov.authservice.repository.RoleRepository;
import org.train.tikhonov.authservice.repository.StatusRepository;
import org.train.tikhonov.authservice.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class FillTables implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StatusRepository statusRepository;

    @Override
    public void run(String... args) throws Exception {
        roleRepository.save(RoleEntity.builder().name("admin").build());
        roleRepository.save(RoleEntity.builder().name("user").build());

        statusRepository.save(StatusEntity.builder().name("locked").build());
        statusRepository.save(StatusEntity.builder().name("unverified").build());
        statusRepository.save(StatusEntity.builder().name("enabled").build());
        statusRepository.save(StatusEntity.builder().name("deleted").build());
    }
}
