package org.example.domain.service;


import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

    String generateToken(UserDetails userDetails);

    String extractUsername(String jwt);
}
