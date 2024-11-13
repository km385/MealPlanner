package com.example.backend.dtos.RecipeIngredient;

import java.math.BigDecimal;

import com.example.backend.dtos.Ingredient.CreateIngredientDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateRecipeIngredientDto {
    @Valid // Add this to ensure nested validation
    @NotNull(message = "Ingredient details are required")
    private CreateIngredientDto ingredient;

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be positive")
    private BigDecimal quantity;
}