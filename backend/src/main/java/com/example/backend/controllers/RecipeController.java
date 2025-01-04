package com.example.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dtos.Recipe.CreateRecipeDto;
import com.example.backend.dtos.Recipe.RecipeDto;
import com.example.backend.dtos.Recipe.UpdateRecipeDto;
import com.example.backend.services.RecipeService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequestMapping("/recipes")
@RestController
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping
    public ResponseEntity<?> getUserRecipes() {
        List<RecipeDto> recipes = recipeService.getCurrentUserRecipes();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserRecipesById(@PathVariable Long id) {
        
        RecipeDto recipe = recipeService.getRecipeById(id);
        return ResponseEntity.ok(recipe);
        
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createRecipe(@Valid @RequestBody CreateRecipeDto dto) { 
        RecipeDto recipe = recipeService.createRecipe(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(recipe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable Long id,
            @Valid @RequestBody UpdateRecipeDto dto) {
        RecipeDto updatedRecipe = recipeService.updateRecipe(id, dto);
        return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok("Recipe deleted");
    }

    // for testing purposes
    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllRecipes() {
        recipeService.deleteAllRecipes();
        return ResponseEntity.ok("All recipes deleted");
    }

    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createRecipes(@Valid @RequestBody List<CreateRecipeDto> dtos) {
        List<RecipeDto> recipes = recipeService.createRecipes(dtos);
        return ResponseEntity.status(HttpStatus.CREATED).body(recipes);
    }
    
}
