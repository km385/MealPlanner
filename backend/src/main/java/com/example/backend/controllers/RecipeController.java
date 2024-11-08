package com.example.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entities.Recipe;
import com.example.backend.entities.User;
import com.example.backend.services.RecipeService;

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
}
