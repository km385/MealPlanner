package com.example.backend.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.dtos.Ingredient.CreateIngredientDto;
import com.example.backend.dtos.Ingredient.IngredientDto;
import com.example.backend.services.IngredientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    // @GetMapping
    // public ResponseEntity<List<IngredientDto>> getAllIngredients() {
    //     return ResponseEntity.ok(ingredientService.getAllIngredients());
    // }

    // @PostMapping
    // public ResponseEntity<IngredientDto> createIngredient(
    //         @Valid @RequestBody CreateIngredientDto dto) {
    //     return ResponseEntity.status(HttpStatus.CREATED)
    //                        .body(ingredientService.createIngredient(dto));
    // }
}
