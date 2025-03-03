package com.example.backend.services;

import java.util.List;
import java.util.stream.Collectors;

import java.util.Set;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.dtos.Recipe.CreateRecipeDto;
import com.example.backend.dtos.Recipe.RecipeDto;
import com.example.backend.dtos.Recipe.UpdateRecipeDto;
import com.example.backend.dtos.RecipeIngredient.CreateRecipeIngredientDto;
import com.example.backend.dtos.RecipeIngredient.UpdateRecipeIngredientDto;
import com.example.backend.entities.Ingredient;
import com.example.backend.entities.Recipe;
import com.example.backend.entities.RecipeIngredient;
import com.example.backend.entities.RecipeIngredientId;
import com.example.backend.entities.User;
import com.example.backend.exceptions.RecipeNotFoundException;
import com.example.backend.mappers.IngredientMapper;
import com.example.backend.mappers.RecipeMapper;
import com.example.backend.repository.IngredientRepository;
import com.example.backend.repository.RecipeRepository;
import com.example.backend.utils.SecurityUtils;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private RecipeMapper recipeMapper;
    @Autowired
    private IngredientMapper ingredientMapper;
    @Autowired
    private SecurityUtils securityUtils;

    public Recipe getRecipeEntity(Long id) {
        Recipe recipe = recipeRepository.findByIdWithIngredients(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        securityUtils.validateRecipeAccess(recipe);
        return recipe;
    }

    public List<RecipeDto> getAllRecipes() {
        return recipeRepository.findAllWithIngredients().stream()
                             .map(recipeMapper::toDTO)
                             .collect(Collectors.toList());
    }

    public RecipeDto getRecipeById(Long id) {

        Recipe recipe = recipeRepository.findByIdWithIngredients(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
        securityUtils.validateRecipeAccess(recipe);
       
        return recipeMapper.toDTO(recipe);
    }

    public RecipeDto createRecipe(CreateRecipeDto createDto) {

        User user = securityUtils.getCurrentUser();
        securityUtils.validateUserAccess(user.getId());

        
        // Create new Recipe entity manually instead of using mapper
        Recipe recipe = recipeMapper.toEntity(createDto);
        recipe.setUser(user);

        // Save recipe first to get its ID
        Recipe savedRecipe = recipeRepository.save(recipe);

        Set<RecipeIngredient> recipeIngredients = new HashSet<>();

        // Create and save new ingredients
        for (CreateRecipeIngredientDto recipeIngredientDto : createDto.getIngredients()) {
            // Convert DTO to entity and save new ingredient
            Ingredient ingredient = ingredientMapper.toEntity(recipeIngredientDto.getIngredient());
            Ingredient savedIngredient = ingredientRepository.save(ingredient);

            // Create recipe-ingredient association
            RecipeIngredient recipeIngredient = new RecipeIngredient();

            // Create and set the composite key
            RecipeIngredientId recipeIngredientId = new RecipeIngredientId();
            recipeIngredientId.setRecipeId(savedRecipe.getId());
            recipeIngredientId.setIngredientId(savedIngredient.getId());
            recipeIngredient.setId(recipeIngredientId);

            // Set the relationships and quantity
            recipeIngredient.setRecipe(savedRecipe);
            recipeIngredient.setIngredient(savedIngredient);
            recipeIngredient.setQuantity(recipeIngredientDto.getQuantity());

            recipeIngredients.add(recipeIngredient);
        }
        savedRecipe.setRecipeIngredients(recipeIngredients);
        savedRecipe = recipeRepository.save(savedRecipe);

        return recipeMapper.toDTO(savedRecipe);
    

    }

    @Transactional
    public RecipeDto updateRecipe(Long id, UpdateRecipeDto dto) {
        Recipe existingRecipe = recipeRepository.findByIdWithIngredients(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));

        securityUtils.validateRecipeAccess(existingRecipe);

        
        if (dto.getName() != null) {
            existingRecipe.setName(dto.getName());
        }
        if (dto.getInstructions() != null) {
            existingRecipe.setInstructions(dto.getInstructions());
        }

        if (dto.getIngredients() != null) {
            existingRecipe.getRecipeIngredients().clear();

            for (UpdateRecipeIngredientDto ingredientDto : dto.getIngredients()) {
                Ingredient ingredient = ingredientRepository.findByNameIgnoreCase(
                        ingredientDto.getIngredient().getName())
                        .orElseGet(() -> {
                            Ingredient newIngredient = new Ingredient();
                            ingredientMapper.updateIngredientFromDTO(ingredientDto.getIngredient(), newIngredient);
                            return ingredientRepository.save(newIngredient);
                        });

                RecipeIngredient recipeIngredient = new RecipeIngredient();
                RecipeIngredientId recipeIngredientId = new RecipeIngredientId();
                recipeIngredientId.setRecipeId(existingRecipe.getId());
                recipeIngredientId.setIngredientId(ingredient.getId());

                recipeIngredient.setId(recipeIngredientId);
                recipeIngredient.setRecipe(existingRecipe);
                recipeIngredient.setIngredient(ingredient);
                recipeIngredient.setQuantity(ingredientDto.getQuantity());

                existingRecipe.getRecipeIngredients().add(recipeIngredient);
            }
        }

        Recipe updatedRecipe = recipeRepository.save(existingRecipe);
        return recipeMapper.toDTO(updatedRecipe);
    }

    @Transactional
    public void deleteRecipe(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));

        securityUtils.validateRecipeAccess(recipe);
        recipe.getMealPlans().clear();
        recipeRepository.delete(recipe);
    }

    public List<RecipeDto> getCurrentUserRecipes() {
        User currentUser = securityUtils.getCurrentUser();
        List<Recipe> recipes = recipeRepository.findByUserId(currentUser.getId());
        return recipeMapper.toDTOList(recipes);
    }

    // for testing
    @Transactional
    public void deleteAllRecipes() {
        recipeRepository.deleteAll();
    }

    @Transactional
    public List<RecipeDto> createRecipes(List<CreateRecipeDto> createDtos) {
        return createDtos.stream()
                .map(this::createRecipe)
                .collect(Collectors.toList());
    }
    
}