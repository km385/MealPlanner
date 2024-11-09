package com.example.backend.dtos.Recipe;

import java.util.List;

import com.example.backend.dtos.RecipeIngredient.RecipeIngredientDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {
    private Long id;
    private String name;
    private String instructions;
    private List<RecipeIngredientDto> ingredients;
    private String createdAt;
    private String updatedAt;
    private String userName;
}
