package org.train.tikhonov.authservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.train.tikhonov.authservice.entity.UserEntity;
import org.train.tikhonov.authservice.repository.UserRepository;
import org.train.tikhonov.authservice.service.AuthenticationService;

@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user =  userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with email %s doesn't exist", email))
        );
        return new JwtUserDetails(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getStatus(),
                user.getRoles()
        );
    }
}
