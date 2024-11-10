package com.example.backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.backend.dtos.Ingredient.CreateIngredientDto;
import com.example.backend.dtos.Ingredient.IngredientDto;
import com.example.backend.dtos.Ingredient.UpdateIngredientDto;
import com.example.backend.entities.Ingredient;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    IngredientDto toDTO(Ingredient ingredient);
    Ingredient toEntity(CreateIngredientDto dto);
    
    @Mapping(target = "id", ignore = true)
    void updateIngredientFromDTO(UpdateIngredientDto dto, @MappingTarget Ingredient ingredient);
}