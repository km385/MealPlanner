package com.example.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.backend.dtos.User.CreateUserDto;
import com.example.backend.dtos.User.UpdateUserDto;
import com.example.backend.dtos.User.UserDto;
import com.example.backend.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDTO(User user);
    
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "recipes", ignore = true)
    User toEntity(CreateUserDto dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "recipes", ignore = true)
    void updateUserFromDTO(UpdateUserDto dto, @MappingTarget User user);
}
