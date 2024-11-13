package com.example.backend.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.backend.dtos.RecipeIngredient.RecipeIngredientDto;
import com.example.backend.dtos.RecipeIngredient.UpdateRecipeIngredientDto;
import com.example.backend.entities.RecipeIngredient;

@Mapper(componentModel = "spring", uses = {IngredientMapper.class})
public interface RecipeIngredientMapper {

    @Mapping(target = "ingredient", source = "ingredient")
    @Mapping(target = "quantity", source = "quantity")
    RecipeIngredientDto toDTO(RecipeIngredient recipeIngredient);
    
    List<RecipeIngredientDto> toDTOList(List<RecipeIngredient> recipeIngredients);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "recipe", ignore = true)
    @Mapping(target = "ingredient", ignore = true)
    void updateRecipeIngredientFromDTO(UpdateRecipeIngredientDto dto, @MappingTarget RecipeIngredient recipeIngredient);

}