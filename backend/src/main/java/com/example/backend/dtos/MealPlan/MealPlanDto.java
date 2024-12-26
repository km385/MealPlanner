package com.example.backend.dtos.MealPlan;

import com.example.backend.entities.DayOfWeek;
import com.example.backend.entities.MealType;

import lombok.Data;

@Data
public class MealPlanDto {
    private Long id;
    private Long recipeId;
    private DayOfWeek dayOfWeek;
    private MealType mealType;
    private String recipeName;
}