package com.acube.docvault.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.acube.docvault.dto.LoginRequest;
import com.acube.docvault.entity.User;
import com.acube.docvault.exception.UserNotFoundException;
import com.acube.docvault.repository.UserRepository;
import com.acube.docvault.security.JwtService;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
private JwtService jwtService;

    public String login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

       if (!passwordEncoder.matches(
        request.getPassword(),
        user.getPassword())) {

    throw new RuntimeException("Invalid password");
}

        return jwtService.generateToken(
        user.getUserId(),
        user.getEmail()
);
    }
}