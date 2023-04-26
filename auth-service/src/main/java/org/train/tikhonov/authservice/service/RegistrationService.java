package org.train.tikhonov.authservice.service;

import io.swagger.annotations.Api;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.train.tikhonov.authservice.dto.RegistrationDto;
import org.train.tikhonov.authservice.entity.MailTokenEntity;
import org.train.tikhonov.authservice.entity.RoleEntity;
import org.train.tikhonov.authservice.entity.StatusEntity;
import org.train.tikhonov.authservice.entity.UserEntity;
import org.train.tikhonov.authservice.exception.EmailException;
import org.train.tikhonov.authservice.exception.InvalidEmailException;
import org.train.tikhonov.authservice.exception.UsernameAlreadyExistsException;
import org.train.tikhonov.authservice.repository.MailTokenRepository;
import org.train.tikhonov.authservice.repository.RoleRepository;
import org.train.tikhonov.authservice.repository.StatusRepository;
import org.train.tikhonov.authservice.repository.UserRepository;
import org.train.tikhonov.authservice.utils.EmailValidator;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StatusRepository statusRepository;
    private final MailTokenRepository mailTokenRepository;

    private final EmailValidator emailValidator;
    private final BCryptPasswordEncoder passwordEncoder;


    @Transactional
    public String register(RegistrationDto request) {
        String email = request.email();

        if (!emailValidator.test(request.email())) {
            throw new InvalidEmailException("Email is not valid");
        }
        userRepository.findByEmail(email).ifPresent(userEntity -> {
            if (userEntity.getStatus().getName().equals("unverified")) {
                throw new EmailException("Please confirm an email");
            }
            throw new UsernameAlreadyExistsException(String.format(
                    "User with email %s already exist? if you cannot enter account, please confirm the email",
                    email)
            );
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
        //System.out.println(mailTokenRepository.findAll());
        // Отпарвка email
        return token;
    }

    @Transactional
    public void confirm(String token) {
        MailTokenEntity mailToken = mailTokenRepository.findById(token).orElseThrow(
                () -> new EmailException("Email token is expired")
        );
        Long personId = Long.valueOf(mailToken.getPersonId());
        userRepository.findById(personId).get().setStatus(statusRepository.findByName("enabled"));
    }

}
