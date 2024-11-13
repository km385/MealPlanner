package com.example.backend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.dtos.User.UpdateUserDto;
import com.example.backend.dtos.User.UserDto;
import com.example.backend.entities.User;
import com.example.backend.mappers.UserMapper;
import com.example.backend.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public User getCurrentUser() {
        // return userRepository.findById(id)
        //     .map(userMapper::toDTO)
        //     .orElseThrow(() -> new RuntimeException(""));
        return new User();
    }

    public UserDto getUser(Long id) {
        return userRepository.findById(id)
                    .map(userMapper::toDTO)
                    .orElseThrow(() -> new RuntimeException("user not found"));
    }

    public UserDto updateUser(Long id, UpdateUserDto dto) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
            
        // if (dto.getEmail() != null && 
        //     !user.getEmail().equals(dto.getEmail()) && 
        //     userRepository.existsByEmail(dto.getEmail())) {
        //     throw new ResourceAlreadyExistsException("User with email " + dto.getEmail() + " already exists");
        // }
        
        // todo: make optional fields, at least one field optional, probably not doable tho
        userMapper.updateUserFromDTO(dto, user);
        if (dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}