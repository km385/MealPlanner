package com.example.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.backend.dtos.Recipe.CreateRecipeDto;
import com.example.backend.dtos.Recipe.RecipeDto;
import com.example.backend.dtos.Recipe.UpdateRecipeDto;
import com.example.backend.entities.Recipe;

@Mapper(componentModel = "spring")
public interface RecipeMapper {
    @Mapping(target = "userName", source = "user.username") 
    RecipeDto toDTO(Recipe recipe);
    Recipe toEntity(CreateRecipeDto dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateRecipeFromDTO(UpdateRecipeDto dto, @MappingTarget Recipe recipe);
}