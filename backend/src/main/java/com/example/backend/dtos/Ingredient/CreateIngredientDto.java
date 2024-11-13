package com.example.backend.dtos.Ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CreateIngredientDto {
    @NotBlank(message = "Ingredient name is required")
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank(message = "Unit of measure is required")
    private String unitOfMeasure;

    private String category;
}
