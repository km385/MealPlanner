package com.example.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dtos.Recipe.CreateRecipeDto;
import com.example.backend.dtos.Recipe.RecipeDto;
import com.example.backend.dtos.Recipe.UpdateRecipeDto;
import com.example.backend.entities.Recipe;
import com.example.backend.entities.User;
import com.example.backend.services.RecipeService;

import jakarta.validation.Valid;

@RequestMapping("/recipes")
@RestController
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/")
    public ResponseEntity<List<Recipe>> allRecipes() {
        List <Recipe> recipes = recipeService.allRecipes();

        return ResponseEntity.ok(recipes);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addRecipe(@RequestBody Recipe recipe) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        recipe.setUser(currentUser);
        recipeService.addRecipe(recipe);
        
        return ResponseEntity.ok("recipe has been added");
    }

    // nowe

    @GetMapping
    public ResponseEntity<List<RecipeDto>> getAllRecipes() {
        List<RecipeDto> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable Long id) {
        
        RecipeDto recipe = recipeService.getRecipeById(id);
        
        return ResponseEntity.ok(recipe);
    }

    @PostMapping
    public ResponseEntity<RecipeDto> createRecipe(@Valid @RequestBody CreateRecipeDto recipeDTO) {
        RecipeDto created = recipeService.createRecipe(recipeDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                           .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDto> updateRecipe(@PathVariable Long id, 
                                                @Valid @RequestBody UpdateRecipeDto recipeDTO) {
        RecipeDto updated = recipeService.updateRecipe(id, recipeDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user")
    public ResponseEntity<List<RecipeDto>> getUserRecipes() {
        List<RecipeDto> recipes = recipeService.getCurrentUserRecipes();
        return ResponseEntity.ok(recipes);
    }
}
