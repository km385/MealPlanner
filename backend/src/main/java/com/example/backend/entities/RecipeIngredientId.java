package com.example.backend.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;

@Embeddable
@EqualsAndHashCode
public class RecipeIngredientId implements Serializable {
    private Long recipeId;
    private Long ingredientId;
    // equals, hashCode
    
}
