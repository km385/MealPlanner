package com.example.backend.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.backend.dtos.MealPlan.MealPlanDto;
import com.example.backend.entities.MealPlan;

@Mapper(componentModel = "spring", uses = {RecipeMapper.class})
public interface MealPlanMapper {
    
    @Mapping(target = "recipeId", source = "recipe.id")
    @Mapping(target = "recipeName", source = "recipe.name")
    MealPlanDto toDTO(MealPlan mealPlan);

    List<MealPlanDto> toDTOList(List<MealPlan> mealPlans);

}