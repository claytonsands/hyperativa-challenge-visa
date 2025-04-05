package org.example.api.controller;

import org.example.api.dto.AuthRequest;
import org.example.domain.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@Tag(name = "Authentication", description = "Login endpoints and token generation")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService jwtService;

    public AuthController(AuthenticationManager authenticationManager, AuthService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/token")
    @Operation(summary = "Generate JWT token", description = "Authenticate the user and return a access token")
    public ResponseEntity<Map<String, String>> getToken(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        String token = jwtService.generateToken((UserDetails) authentication.getPrincipal());

        return ResponseEntity.ok(Map.of("access_token", token));
    }
}
