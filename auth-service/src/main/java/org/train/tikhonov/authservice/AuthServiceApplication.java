package org.train.tikhonov.authservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.train.tikhonov.authservice.entity.RoleEntity;
import org.train.tikhonov.authservice.entity.StatusEntity;
import org.train.tikhonov.authservice.entity.UserEntity;
import org.train.tikhonov.authservice.repository.RoleRepository;
import org.train.tikhonov.authservice.repository.StatusRepository;
import org.train.tikhonov.authservice.repository.UserRepository;

import java.util.Set;

@SpringBootApplication
@EnableDiscoveryClient
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadInitData(
            RoleRepository roleRepository,
            StatusRepository statusRepository,
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder
    ) {
        return args -> {
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
        };
    }

}
