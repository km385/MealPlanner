package com.example.backend.dtos.Ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateIngredientDto {
    @Size(min = 2, max = 100, message = "Ingredient name must be between 2 and 100 characters")
    private String name;
    
    @NotBlank(message = "Unit of measure is required")
    @Pattern(regexp = "^(grams|kilograms|milliliters|liters|pieces|tablespoons|teaspoons|cups)$", 
             message = "Invalid unit of measure.")
    private String unitOfMeasure;
}
