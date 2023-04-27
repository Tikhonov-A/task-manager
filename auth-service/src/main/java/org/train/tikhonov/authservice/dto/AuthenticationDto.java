package org.train.tikhonov.authservice.dto;


import org.springframework.lang.NonNull;

public record AuthenticationDto(
        @NonNull String email,
        @NonNull String password)
{ }
