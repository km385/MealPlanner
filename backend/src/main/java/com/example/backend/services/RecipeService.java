package com.example.backend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.backend.entities.Recipe;
import com.example.backend.repository.RecipeRepository;


@Service
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;

    
    public List<Recipe> allRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        recipeRepository.findAll().forEach(recipes::add);

        return recipes;
    }

    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    
}