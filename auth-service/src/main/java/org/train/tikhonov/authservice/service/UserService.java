package org.train.tikhonov.authservice.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.train.tikhonov.authservice.entity.RoleEntity;
import org.train.tikhonov.authservice.entity.UserEntity;
import org.train.tikhonov.authservice.excepetion.UserNotFound;
import org.train.tikhonov.authservice.repository.UserRepository;
import org.train.tikhonov.authservice.utils.JwtUtils;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService  {

    private final UserRepository userRepository;


    @Transactional
    public String authenticate(String username) {
        UserEntity user = userRepository.findUserEntityByUsername(username).orElseThrow(
                () -> new UserNotFound(String.format("User with username %s doesn't exist", username))
        );
        return JwtUtils.createToken(user.getUsername(),
                user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet())
        );
    }

}
