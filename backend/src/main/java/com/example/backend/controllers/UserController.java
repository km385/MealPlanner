package com.example.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dtos.User.UpdateUserDto;
import com.example.backend.dtos.User.UserDto;
import com.example.backend.services.UserService;
import com.example.backend.utils.SecurityUtils;

import jakarta.validation.Valid;

@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityUtils securityUtils;


    // probably not good since no admin role exists
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> allUsers() {
        List<UserDto> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/me")
    public UserDto authenticatedUser() {
        return userService.getUser(securityUtils.getCurrentUser().getId());
    }

    @PutMapping("/me")
    public UserDto updateCurrentUser(@Valid @RequestBody UpdateUserDto dto) {
        return userService.updateUser(securityUtils.getCurrentUser().getId(), dto);
    }

    @DeleteMapping("/me")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCurrentUser() {
        userService.deleteUser(securityUtils.getCurrentUser().getId());
    }
}