package org.train.tikhonov.authservice.rest;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.train.tikhonov.authservice.dto.RegistrationDto;
import org.train.tikhonov.authservice.service.RegistrationService;

@RestController
@RequestMapping("api/v1/registration")
@RequiredArgsConstructor
public class RegistrationRestController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegistrationDto request) {
        //registrationService.register(request);
        return ResponseEntity.ok(registrationService.register(request));
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmEmail(@RequestParam String token) {
        registrationService.confirm(token);
        return ResponseEntity.ok("good");
    }
}
