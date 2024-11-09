package com.example.backend.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.dtos.Recipe.CreateRecipeDto;
import com.example.backend.dtos.Recipe.RecipeDto;
import com.example.backend.dtos.Recipe.UpdateRecipeDto;
import com.example.backend.entities.Recipe;
import com.example.backend.entities.User;
import com.example.backend.mappers.RecipeMapper;
import com.example.backend.repository.RecipeRepository;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RecipeMapper recipeMapper;

    
    public List<Recipe> allRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        recipeRepository.findAll().forEach(recipes::add);

        return recipes;
    }

    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    // nowe

    public List<RecipeDto> getAllRecipes() {
        return recipeRepository.findAllWithIngredients().stream()
                             .map(recipeMapper::toDTO)
                             .collect(Collectors.toList());
    }

    public RecipeDto getRecipeById(Long id) {
        
        Recipe recipe = recipeRepository.findByIdWithIngredients(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found")/*new RecipeNotFoundException(id)*/);
        RecipeDto recipeDto = recipeMapper.toDTO(recipe);
        return recipeDto;
    }

    public RecipeDto createRecipe(CreateRecipeDto dto) {
        User currentUser = userService.getCurrentUser();
        
        // Check if user already has a recipe with this name
        if (recipeRepository.existsByNameAndUserId(dto.getName(), currentUser.getId())) {
            // throw new DuplicateRecipeException(dto.getName());
        }

        Recipe recipe = recipeMapper.toEntity(dto);
        recipe.setUser(currentUser);
        
        Recipe saved = recipeRepository.save(recipe);
        return recipeMapper.toDTO(saved);
    }

    public RecipeDto updateRecipe(Long id, UpdateRecipeDto dto) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found")/*new RecipeNotFoundException(id)*/);

        // Check if user owns the recipe
        User currentUser = userService.getCurrentUser();
        if (!existingRecipe.getUser().getId().equals(currentUser.getId())) {
            // throw new UnauthorizedAccessException("You don't have permission to modify this recipe");
        }

        recipeMapper.updateRecipeFromDTO(dto, existingRecipe);
        Recipe updated = recipeRepository.save(existingRecipe);
        return recipeMapper.toDTO(updated);
    }

    public void deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found")/*new RecipeNotFoundException(id)*/);

        // Check if user owns the recipe
        User currentUser = userService.getCurrentUser();
        if (!recipe.getUser().getId().equals(currentUser.getId())) {
            // throw new UnauthorizedAccessException("You don't have permission to delete this recipe");
        }

        recipeRepository.delete(recipe);
    }

    public List<RecipeDto> getCurrentUserRecipes() {
        User currentUser = userService.getCurrentUser();
        return recipeRepository.findByUserId(currentUser.getId()).stream()
                             .map(recipeMapper::toDTO)
                             .collect(Collectors.toList());
    }

    
}