package com.example.backend.exceptions;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(Long id) {
        super("Recipe not found with id: " + id);
    }
}
