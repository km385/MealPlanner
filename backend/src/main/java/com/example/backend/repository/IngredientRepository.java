package com.example.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.entities.Ingredient;
import com.example.backend.entities.Recipe;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    // List<Recipe> findByUserId(Long userId);
    
    // @Query("SELECT r FROM Ingredient r LEFT JOIN FETCH r.recipeIngredients WHERE r.id = :id")
    // Optional<Recipe> findByIdWithIngredients(@Param("id") Long id);
    
    // @Query("SELECT r FROM Ingredient r LEFT JOIN FETCH r.recipeIngredients")
    // List<Ingredient> findAllWithIngredients();

    // boolean existsByNameAndUserId(String name, Long userId);
    
}
