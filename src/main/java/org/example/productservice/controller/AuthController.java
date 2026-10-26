package org.example.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.productservice.dto.LoginRequest;
import org.example.productservice.dto.LoginResponse;
import org.example.productservice.dto.RegisterRequest;
import org.example.productservice.entity.AppUser;
import org.example.productservice.enums.Role;
import org.example.productservice.repository.UserRepository;
import org.example.productservice.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager
            authenticationManager;

    private final JwtService jwtService;

    @PostMapping("/register")
    public String register(
            @RequestBody RegisterRequest request) {

        AppUser user = AppUser.builder()
                .username(request.username())
                .password(
                        passwordEncoder.encode(
                                request.password()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }
    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()));

        String token =
                jwtService.generateToken(
                        request.username());

        return new LoginResponse(token);
    }

}