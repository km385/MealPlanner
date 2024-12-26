package com.example.backend.entities;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@EqualsAndHashCode
@Setter
@Getter
public class RecipeIngredientId implements Serializable {
    private Long recipeId;
    private Long ingredientId;
    
}
