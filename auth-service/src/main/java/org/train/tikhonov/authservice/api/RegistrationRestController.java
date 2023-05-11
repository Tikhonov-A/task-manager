package org.train.tikhonov.authservice.api;


import jakarta.ws.rs.HttpMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.train.tikhonov.authservice.dto.RegistrationDto;
import org.train.tikhonov.authservice.service.RegistrationService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1/registration")
@RequiredArgsConstructor
public class RegistrationRestController {

    private final RegistrationService registrationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> register(@RequestBody RegistrationDto request) {
        String token = registrationService.register(request);
        Map<String, String> response = new HashMap<>();
        response.put("message", "confirm your registration");
        response.put("token", token);
        return response;
    }

    @PostMapping("/confirm")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> confirmEmail(@RequestParam String token) {
        registrationService.confirm(token);
        return Map.of("message", "Email confirmed");
    }
}
