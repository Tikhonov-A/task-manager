package org.train.tikhonov.authservice.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.train.tikhonov.authservice.entity.RoleEntity;
import org.train.tikhonov.authservice.entity.UserEntity;
import org.train.tikhonov.authservice.repository.UserRepository;
import org.train.tikhonov.authservice.utils.JwtUtils;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;


    @Transactional
    public String authenticate(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with email %s doesn't exist", email))
        );
        return JwtUtils.createToken(user.getUsername(),
                user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet())
        );
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with email %s doesn't exist", email))
        );
    }
}
