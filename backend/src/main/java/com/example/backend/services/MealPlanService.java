package com.example.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.dtos.MealPlan.MealPlanDto;
import com.example.backend.entities.DayOfWeek;
import com.example.backend.entities.MealPlan;
import com.example.backend.entities.MealType;
import com.example.backend.entities.Recipe;
import com.example.backend.entities.User;
import com.example.backend.exceptions.MealForTheDayNotFound;
import com.example.backend.mappers.MealPlanMapper;
import com.example.backend.repository.MealPlanRepository;
import com.example.backend.utils.SecurityUtils;
import java.util.List;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MealPlanService {
    @Autowired
    private MealPlanRepository mealPlanRepository;
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private MealPlanMapper mealPlanMapper;

    public MealPlanDto assignRecipeToDay(Long recipeId, DayOfWeek day, MealType mealType) {
        User currentUser = securityUtils.getCurrentUser();
        Recipe recipe = recipeService.getRecipeEntity(recipeId);

        // replace the existing meal plan if it exists
        MealPlan mealPlan = mealPlanRepository
                .findByUserIdAndDayOfWeekAndMealType(currentUser.getId(), day, mealType)
                .map(existing -> {
                    existing.setRecipe(recipe);
                    return existing;
                })
                .orElse(new MealPlan(currentUser, recipe, day, mealType));

        return mealPlanMapper.toDTO(mealPlanRepository.save(mealPlan));
    }

    public List<MealPlanDto> getRecipesForDay(DayOfWeek day) {
        User currentUser = securityUtils.getCurrentUser();
        List<MealPlan> mealPlans = mealPlanRepository
                .findByUserIdAndDayOfWeek(currentUser.getId(), day);

        if (mealPlans.isEmpty()) {
            throw new MealForTheDayNotFound(currentUser.getId(), day);
        }

        return mealPlanMapper.toDTOList(mealPlans);
    }

    public List<MealPlanDto> getRecipesForWeek() {
        User currentUser = securityUtils.getCurrentUser();
        return mealPlanMapper.toDTOList(
                mealPlanRepository.findByUserIdOrderByDayOfWeek(currentUser.getId()));
    }

    public void removeRecipeFromDay(DayOfWeek day, MealType mealType) {
        User currentUser = securityUtils.getCurrentUser();
        mealPlanRepository.deleteByUserIdAndDayOfWeekAndMealType(
                currentUser.getId(),
                day,
                mealType);
    }
}