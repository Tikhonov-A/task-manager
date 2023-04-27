package org.train.tikhonov.authservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.train.tikhonov.authservice.dto.RegistrationDto;
import org.train.tikhonov.authservice.entity.MailTokenEntity;
import org.train.tikhonov.authservice.entity.RoleEntity;
import org.train.tikhonov.authservice.entity.StatusEntity;
import org.train.tikhonov.authservice.entity.UserEntity;
import org.train.tikhonov.authservice.exception.TokenException;
import org.train.tikhonov.authservice.exception.UsernameAlreadyExistsException;
import org.train.tikhonov.authservice.repository.MailTokenRepository;
import org.train.tikhonov.authservice.repository.RoleRepository;
import org.train.tikhonov.authservice.repository.StatusRepository;
import org.train.tikhonov.authservice.repository.UserRepository;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StatusRepository statusRepository;

    private final MailTokenRepository mailTokenRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    private final String CONFIRM_TOKEN_MESSAGE = "Please confirm your email";
    private final String ACCOUNT_ALREADY_EXISTS_MESSAGE = "User with email %s already exist";


    @Transactional
    public String register(RegistrationDto request) {
        String email = request.email();

        userRepository.findByEmail(email).ifPresent(userEntity -> {
            if (userEntity.getStatus().getName().equals("unverified")) {
                throw new TokenException(CONFIRM_TOKEN_MESSAGE);
            }
            throw new UsernameAlreadyExistsException(String.format(ACCOUNT_ALREADY_EXISTS_MESSAGE, email));
        });

        StatusEntity status = statusRepository.findByName("unverified");
        Set<RoleEntity> roles = roleRepository.findAllByName("user");
        UserEntity user = userRepository.saveAndFlush(
                UserEntity.builder().
                        firstName(request.firstName()).
                        lastName(request.lastName()).
                        email(request.email()).
                        password(passwordEncoder.encode(request.password())).
                        status(status).
                        roles(roles).
                build()
        );
        String token = UUID.randomUUID().toString();
        MailTokenEntity mailToken = new MailTokenEntity(token, user.getId().toString());
        mailTokenRepository.save(mailToken);
        return token;
    }

    @Transactional
    public void confirm(String token) {
        MailTokenEntity mailToken = mailTokenRepository.findById(token).orElseThrow(
                () -> new TokenException("Token is expired or incorrect link")
        );
        Long personId = Long.valueOf(mailToken.getPersonId());
        userRepository.findById(personId).get().setStatus(statusRepository.findByName("enabled"));
    }

}
