package com.example.backend.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.backend.dtos.Ingredient.CreateIngredientDto;
import com.example.backend.dtos.Ingredient.IngredientDto;
import com.example.backend.entities.Ingredient;
import com.example.backend.mappers.IngredientMapper;
import com.example.backend.repository.IngredientRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    public List<IngredientDto> getAllIngredients() {
        return ingredientRepository.findAll().stream()
                                 .map(ingredientMapper::toDTO)
                                 .collect(Collectors.toList());
    }

    public IngredientDto createIngredient(CreateIngredientDto dto) {
        Ingredient ingredient = ingredientMapper.toEntity(dto);
        Ingredient saved = ingredientRepository.save(ingredient);
        
        return ingredientMapper.toDTO(saved);
    }
}
