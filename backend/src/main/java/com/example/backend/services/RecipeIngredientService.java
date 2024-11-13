package com.example.backend.services;

import org.springframework.stereotype.Service;

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


    // public void addIngredientsToRecipe(Long recipeId, List<CreateRecipeIngredientDto> ingredients) {
    //     Recipe recipe = recipeRepository.findById(recipeId)
    //         .orElseThrow(() -> new ResourceNotFoundException("Recipe not found with id: " + recipeId));
            
    //     for (CreateRecipeIngredientDto ingredientDto : ingredients) {
    //         Ingredient ingredient = ingredientRepository.findById(ingredientDto.getIngredientId())
    //             .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found with id: " + ingredientDto.getIngredientId()));
                
    //         RecipeIngredient recipeIngredient = new RecipeIngredient();
    //         RecipeIngredientId id = new RecipeIngredientId();
    //         id.setRecipeId(recipeId);
    //         id.setIngredientId(ingredient.getId());
            
    //         recipeIngredient.setId(id);
    //         recipeIngredient.setRecipe(recipe);
    //         recipeIngredient.setIngredient(ingredient);
    //         recipeIngredient.setQuantity(ingredientDto.getQuantity());
            
    //         recipeIngredientRepository.save(recipeIngredient);
    //     }
    // }

    

}
