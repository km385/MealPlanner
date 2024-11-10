package com.example.backend.services;

import org.springframework.stereotype.Service;

import com.example.backend.dtos.RecipeIngredient.CreateRecipeIngredientDto;
import com.example.backend.dtos.RecipeIngredient.RecipeIngredientDto;
import com.example.backend.entities.Ingredient;
import com.example.backend.entities.Recipe;
import com.example.backend.entities.RecipeIngredient;
import com.example.backend.mappers.RecipeIngredientMapper;
import com.example.backend.repository.IngredientRepository;
import com.example.backend.repository.RecipeIngredientRepository;
import com.example.backend.repository.RecipeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeIngredientService {
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final RecipeIngredientMapper recipeIngredientMapper;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    // public RecipeIngredientDto addIngredientToRecipe(
    //         Long recipeId, 
    //         CreateRecipeIngredientDto dto) {
    //     Recipe recipe = recipeRepository.findById(recipeId)
    //             .orElseThrow(() -> new RuntimeException(""));
                
    //     Ingredient ingredient = ingredientRepository.findById(dto.getIngredientId())
    //             .orElseThrow(() -> new RuntimeException(""));

    //     RecipeIngredient recipeIngredient = recipeIngredientMapper.toEntity(dto);
    //     recipeIngredient.setRecipe(recipe);
    //     recipeIngredient.setIngredient(ingredient);
        
    //     RecipeIngredient saved = recipeIngredientRepository.save(recipeIngredient);
    //     return recipeIngredientMapper.toDTO(saved);
    // }
}
