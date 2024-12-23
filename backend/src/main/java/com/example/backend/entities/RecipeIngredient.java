package com.example.backend.entities;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "recipe_ingredients")
@Getter
@Setter
public class RecipeIngredient {
    @EmbeddedId
    private RecipeIngredientId id;
    
    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
    
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;
    
    private BigDecimal quantity;
    // getters, setters
}
