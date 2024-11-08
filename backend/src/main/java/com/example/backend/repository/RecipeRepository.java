package com.example.backend.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.entities.Recipe;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {
    
}