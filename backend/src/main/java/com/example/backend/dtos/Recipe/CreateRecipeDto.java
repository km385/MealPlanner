package com.example.backend.dtos.Recipe;

import java.util.List;

import com.example.backend.dtos.RecipeIngredient.CreateRecipeIngredientDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRecipeDto {
    @NotBlank(message = "Recipe name is required")
    @Size(min = 3, max = 100, message = "Recipe name must be between 3 and 100 characters")
    private String name;

    @Size(max = 1000, message = "Instructions cannot exceed 1000 characters")
    private String instructions;

    @NotEmpty(message = "At least one ingredient is required")
    @Valid
    private List<CreateRecipeIngredientDto> ingredients;
}
