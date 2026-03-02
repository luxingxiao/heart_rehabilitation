package com.heart.rehabilitation.controller;

import com.heart.rehabilitation.dto.JwtResponse;
import com.heart.rehabilitation.dto.LoginRequest;
import com.heart.rehabilitation.dto.RegisterRequest;
import com.heart.rehabilitation.model.User;
import com.heart.rehabilitation.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = authService.authenticate(loginRequest);
        Authentication authentication = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        Set<String> roles = authService.getRolesFromAuthentication(authentication);
        return ResponseEntity.ok(new JwtResponse(token, authentication.getName(), roles));
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest registerRequest) {
        User user = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}