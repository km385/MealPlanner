package com.example.backend.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.services.IngredientService;

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
