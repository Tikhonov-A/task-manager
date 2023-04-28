package org.train.tikhonov.authservice.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.train.tikhonov.authservice.dto.AuthenticationDto;
import org.train.tikhonov.authservice.entity.RoleEntity;
import org.train.tikhonov.authservice.entity.UserEntity;
import org.train.tikhonov.authservice.repository.UserRepository;

import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AuthenticationService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;


    @Transactional
    public Map<String, String> authenticate(AuthenticationDto request) {
        UserEntity user = (UserEntity) loadUserByUsername(request.email());
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BadCredentialsException("Password is incorrect");
        }
        String accessToken = jwtService.createAccessToken(user.getUsername(),
                user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet())
        );

        String refreshToken = jwtService.createRefreshToken(user.getUsername(),
                user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet())
        );

        return Map.of("access_token", accessToken, "refresh_token", refreshToken);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with email %s doesn't exist", email))
        );
    }
}
