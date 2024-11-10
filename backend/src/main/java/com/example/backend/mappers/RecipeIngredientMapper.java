package com.example.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.backend.dtos.RecipeIngredient.RecipeIngredientDto;
import com.example.backend.entities.RecipeIngredient;

@Mapper(componentModel = "spring")
public interface RecipeIngredientMapper {
    @Mapping(target = "ingredientName", source = "ingredient.name")
    @Mapping(target = "unitOfMeasure", source = "ingredient.unitOfMeasure")
    RecipeIngredientDto toDTO(RecipeIngredient recipeIngredient);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "recipe", ignore = true)
    RecipeIngredient toEntity(RecipeIngredientDto dto);
}