package com.example.backend.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dtos.LoginUserDto;
import com.example.backend.dtos.RegisterUserDto;
import com.example.backend.entities.User;
import com.example.backend.responses.LoginResponse;
import com.example.backend.services.AuthenticationService;
import com.example.backend.services.JwtService;

import jakarta.validation.Valid;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterUserDto registerUserDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            
            bindingResult.getAllErrors().forEach(error -> {
                // Split the error message based on field and description
                String field = ((org.springframework.validation.FieldError) error).getField();
                String message = error.getDefaultMessage();
                
                errors.put(field, message);
            });

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }


        User registeredUser = authenticationService.signup(registerUserDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            
            bindingResult.getAllErrors().forEach(error -> {
                // Split the error message based on field and description
                String field = ((org.springframework.validation.FieldError) error).getField();
                String message = error.getDefaultMessage();
                
                errors.put(field, message);
            });

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
