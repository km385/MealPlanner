package com.example.backend.dtos.Ingredient;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateIngredientDto {
    @Size(min = 2, max = 100)
    private String name;
    @Size(min = 2, max = 100)
    private String unitOfMeasure;
    @Size(min = 2, max = 100)
    private String category;
}
