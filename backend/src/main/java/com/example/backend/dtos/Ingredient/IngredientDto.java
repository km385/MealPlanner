package com.example.backend.dtos.Ingredient;

import lombok.Data;

@Data
public class IngredientDto {
    private Long id;
    private String name;
    private String unitOfMeasure;
}