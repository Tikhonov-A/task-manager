package org.train.tikhonov.authservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.train.tikhonov.authservice.dto.AuthenticationDto;
import org.train.tikhonov.authservice.entity.RoleEntity;
import org.train.tikhonov.authservice.entity.StatusEntity;
import org.train.tikhonov.authservice.entity.UserEntity;
import org.train.tikhonov.authservice.repository.UserRepository;

import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationServiceTest(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private AuthenticationService testedService;

    @BeforeEach
    void setUp() {
        testedService = new AuthenticationService(
                userRepository,
                jwtService,
                passwordEncoder
        );
    }


    @Test
    void itShouldAuthenticateCorrectly() {
        String email = "aleksandr.tihonov.02@mail.ru";

        Optional<UserEntity> user = Optional.of(
                new UserEntity(
                        "aleksandr",
                        "tikhonov",
                        "aleksandr.tihonov.02@mail.ru",
                        passwordEncoder.encode("12345"),
                        new StatusEntity("status"),
                        Set.of(new RoleEntity("role"))
                )
        );

        when(userRepository.findByEmail(email)).thenReturn(user);
        //when(passwordEncoder.matches(any(), any())).thenReturn(true);
        when(jwtService.createAccessToken(any(), any())).thenReturn("df");
        when(jwtService.createRefreshToken(any(), any())).thenReturn("sdf");
        testedService.authenticate(new AuthenticationDto(email, "12345"));

    }

    @Test
    void itShouldLoadUserByUsername() {
        String email = "aleksandr.tihonov.02@mail.ru";
        Optional<UserEntity> user = Optional.of(
                new UserEntity(
                        "aleksandr",
                        "tikhonov",
                        "aleksandr.tihonov.02@mail.ru",
                        passwordEncoder.encode("12345"),
                        new StatusEntity("status"),
                        Set.of(new RoleEntity("role"))
                )
        );

        when(userRepository.findByEmail(email)).thenReturn(user);

        testedService.loadUserByUsername(email);
    }
}