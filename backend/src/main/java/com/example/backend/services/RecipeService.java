package com.example.backend.services;

import java.util.ArrayList;
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
import com.example.backend.mappers.IngredientMapper;
import com.example.backend.mappers.RecipeMapper;
import com.example.backend.repository.IngredientRepository;
import com.example.backend.repository.RecipeIngredientRepository;
import com.example.backend.repository.RecipeRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.utils.SecurityUtils;

import jakarta.transaction.Transactional;


@Service
@Transactional
public class RecipeService {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;
    @Autowired
    private RecipeIngredientService recipeIngredientService;
    @Autowired
    private UserService userService;
    @Autowired
    private RecipeMapper recipeMapper;
    @Autowired
    private IngredientMapper ingredientMapper;
    @Autowired
    private SecurityUtils securityUtils;

    
    public RecipeDto createRecipe(CreateRecipeDto createDto, Long userId) {
        securityUtils.validateUserAccess(userId);
        User user = securityUtils.getCurrentUser();
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



    
    public List<Recipe> allRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        recipeRepository.findAll().forEach(recipes::add);

        return recipes;
    }

    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    // nowe

    public List<RecipeDto> getAllRecipes() {
        return recipeRepository.findAllWithIngredients().stream()
                             .map(recipeMapper::toDTO)
                             .collect(Collectors.toList());
    }

    public RecipeDto getRecipeById(Long id) {
        
        Recipe recipe = recipeRepository.findByIdWithIngredients(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found")/*new RecipeNotFoundException(id)*/);
        RecipeDto recipeDto = recipeMapper.toDTO(recipe);
        return recipeDto;
    }

    public RecipeDto createRecipe(CreateRecipeDto dto) {
        User currentUser = userService.getCurrentUser();
        
        // Check if user already has a recipe with this name
        if (recipeRepository.existsByNameAndUserId(dto.getName(), currentUser.getId())) {
            // throw new DuplicateRecipeException(dto.getName());
        }

        Recipe recipe = recipeMapper.toEntity(dto);
        recipe.setUser(currentUser);
        
        Recipe saved = recipeRepository.save(recipe);
        return recipeMapper.toDTO(saved);
    }

    @Transactional
    public RecipeDto updateRecipe(Long id, UpdateRecipeDto dto) {
        Recipe existingRecipe = recipeRepository.findByIdWithIngredients(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

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
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        securityUtils.validateRecipeAccess(recipe);
        
        // The cascade settings will handle deletion of recipe ingredients and ingredients
        recipeRepository.delete(recipe);
        recipeRepository.flush();
    }

    // public List<RecipeDto> getCurrentUserRecipes() {
    //     User currentUser = userService.getCurrentUser();
    //     return recipeRepository.findByUserId(currentUser.getId()).stream()
    //                          .map(recipeMapper::toDTO)
    //                          .collect(Collectors.toList());
    // }

    public List<RecipeDto> getUserRecipes(Long userId) {
        securityUtils.validateUserAccess(userId);
        return recipeMapper.toDTOList(recipeRepository.findByUserId(userId));
    }

    
   
    
}