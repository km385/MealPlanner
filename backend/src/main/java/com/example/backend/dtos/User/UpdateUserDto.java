package com.example.backend.dtos.User;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserDto {
    @Size(min = 2, max = 100)
    private String fullName;
    
    @Size(min = 6)
    private String password;
}