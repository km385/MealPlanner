package com.example.backend.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.backend.entities.RecipeIngredient;
import com.example.backend.entities.RecipeIngredientId;

@Repository
public interface RecipeIngredientRepository extends CrudRepository<RecipeIngredient, RecipeIngredientId> {
    // List<Recipe> findByUserId(Long userId);
    
    // @Query("SELECT r FROM Ingredient r LEFT JOIN FETCH r.recipeIngredients WHERE r.id = :id")
    // Optional<RecipeIngredient> findByIdWithIngredients(@Param("id") Long id);
    
    // @Query("SELECT r FROM Ingredient r LEFT JOIN FETCH r.recipeIngredients")
    // List<Ingredient> findAllWithIngredients();

    // boolean existsByNameAndUserId(String name, Long userId);

    List<RecipeIngredient> findByRecipeId(Long recipeId);
    List<RecipeIngredient> findByIngredientId(Long ingredientId);
    void deleteByRecipeId(Long recipeId);
}
