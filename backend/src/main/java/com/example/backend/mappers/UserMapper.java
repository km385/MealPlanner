package com.example.backend.mappers;
import java.util.List;

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
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toEntity(CreateUserDto dto);

    List<UserDto> toDTOList(List<User> users);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "recipes", ignore = true)
    void updateUserFromDTO(UpdateUserDto dto, @MappingTarget User user);
}
