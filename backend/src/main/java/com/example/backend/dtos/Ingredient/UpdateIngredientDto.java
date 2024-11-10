package com.example.backend.dtos.Ingredient;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateIngredientDto {
    @Size(min = 2, max = 100)
    private String name;
    private String unitOfMeasure;
    private String category;
}
