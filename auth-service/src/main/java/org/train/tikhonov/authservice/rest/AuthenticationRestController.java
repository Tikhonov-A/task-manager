package org.train.tikhonov.authservice.rest;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.train.tikhonov.authservice.dto.AuthenticationDto;
import org.train.tikhonov.authservice.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationRestController {

    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationDto requestBody) {
        String token = userService.authenticate(requestBody.username());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

}
