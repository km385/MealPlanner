package com.example.backend.dtos.Recipe;

import java.util.List;

import com.example.backend.dtos.RecipeIngredient.UpdateRecipeIngredientDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateRecipeDto {
    @Size(min = 3, max = 100, message = "Name cannot exceed 100 characters")
    private String name;
    
    @Size(max = 1000, message = "Instructions cannot exceed 1000 characters")
    private String instructions;
    
    @Valid
    private List<UpdateRecipeIngredientDto> ingredients;
}