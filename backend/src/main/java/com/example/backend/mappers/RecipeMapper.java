package com.example.backend.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.backend.dtos.Recipe.CreateRecipeDto;
import com.example.backend.dtos.Recipe.RecipeDto;
import com.example.backend.dtos.Recipe.UpdateRecipeDto;
import com.example.backend.entities.Recipe;

@Mapper(componentModel = "spring", uses = {RecipeIngredientMapper.class})
public interface RecipeMapper {
    
    @Mapping(target = "ingredients", source = "recipeIngredients")
    RecipeDto toDTO(Recipe recipe);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "recipeIngredients", ignore = true)
    Recipe toEntity(CreateRecipeDto dto);

    List<RecipeDto> toDTOList(List<Recipe> recipes);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "recipeIngredients", source = "ingredients")
    void updateRecipeFromDTO(UpdateRecipeDto dto, @MappingTarget Recipe recipe);
}