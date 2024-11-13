package com.example.backend.dtos.RecipeIngredient;

import java.math.BigDecimal;

import com.example.backend.dtos.Ingredient.IngredientDto;

import lombok.Data;

@Data
public class RecipeIngredientDto {
    // private Long recipeId;
    // private Long ingredientId;
    // private String ingredientName;
    // private String unitOfMeasure;
    // private BigDecimal quantity;
    private IngredientDto ingredient;
    private BigDecimal quantity;
}
