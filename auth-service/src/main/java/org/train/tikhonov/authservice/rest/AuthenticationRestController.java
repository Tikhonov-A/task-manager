package org.train.tikhonov.authservice.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.train.tikhonov.authservice.dto.AuthenticationDto;
import org.train.tikhonov.authservice.service.AuthService;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
@Api(description = "dsfsfs")
public class AuthenticationRestController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationDto requestBody) {
        String token = authService.authenticate(requestBody.username());
        return ResponseEntity.ok(token);
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

}
