package com.example.backend.dtos.RecipeIngredient;

import java.math.BigDecimal;

import com.example.backend.dtos.Ingredient.UpdateIngredientDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateRecipeIngredientDto {
    @Positive(message = "Quantity must be positive")
    @NotNull(message = "Quantity is required")
    @DecimalMax(value = "10000.0", message = "Quantity cannot exceed 10000")
    @Digits(integer = 6, fraction = 2, message = "Quantity must have at most 6 digits and 2 decimal places")
    private BigDecimal quantity;

    @Valid
    private UpdateIngredientDto ingredient;
}
