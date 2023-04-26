package org.train.tikhonov.authservice.dto;

public record RegistrationDto(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
