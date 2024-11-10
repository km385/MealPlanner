package com.example.backend.dtos.RecipeIngredient;

import java.math.BigDecimal;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateRecipeIngredientDto {
    @Positive(message = "Quantity must be positive")
    private BigDecimal quantity;
}
