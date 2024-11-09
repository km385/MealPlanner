package com.example.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backend.entities.Recipe;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findByUserId(Long userId);
    
    @Query("SELECT r FROM Recipe r LEFT JOIN FETCH r.recipeIngredients WHERE r.id = :id")
    Optional<Recipe> findByIdWithIngredients(@Param("id") Long id);
    
    @Query("SELECT r FROM Recipe r LEFT JOIN FETCH r.recipeIngredients")
    List<Recipe> findAllWithIngredients();

    boolean existsByNameAndUserId(String name, Long userId);
}