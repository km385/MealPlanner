package com.example.backend.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.example.backend.utils.SecurityUtils;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequestMapping("/recipes")
@RestController
public class RecipeController {
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private SecurityUtils securityUtils;

    @GetMapping
    public List<RecipeDto> getUserRecipes() {
        return recipeService.getUserRecipes(securityUtils.getCurrentUser().getId());
    }

    @GetMapping("/{id}")
    public RecipeDto getUserRecipesById(@PathVariable Long id) {
        return recipeService.getRecipeById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeDto createRecipe(@Valid @RequestBody CreateRecipeDto dto) {
        return recipeService.createRecipe(dto, securityUtils.getCurrentUser().getId());
    }

    @PutMapping("/{id}")
    public String putMethodName(@PathVariable Long id, @Valid @RequestBody UpdateRecipeDto dto) {
        recipeService.updateRecipe(id, dto);
        
        return "receipe updated";
    }

    @DeleteMapping("/{id}")
    public String deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return "recipe deleted";
    }




    // @GetMapping("/")
    // public ResponseEntity<List<Recipe>> allRecipes() {
    //     List <Recipe> recipes = recipeService.allRecipes();

    //     return ResponseEntity.ok(recipes);
    // }

    // @PostMapping("/add")
    // public ResponseEntity<String> addRecipe(@RequestBody Recipe recipe) {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     User currentUser = (User) authentication.getPrincipal();
    //     recipe.setUser(currentUser);
    //     recipeService.addRecipe(recipe);
        
    //     return ResponseEntity.ok("recipe has been added");
    // }

    
}
