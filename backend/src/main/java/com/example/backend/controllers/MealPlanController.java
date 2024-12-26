package com.example.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.example.backend.dtos.MealPlan.MealPlanDto;
import com.example.backend.entities.DayOfWeek;
import com.example.backend.entities.MealType;
import com.example.backend.services.MealPlanService;

@RestController
@RequestMapping("/meal-plan")
public class MealPlanController {
    @Autowired
    private MealPlanService mealPlanService;

    @PostMapping("/{recipeId}/{day}/{mealType}")
    public ResponseEntity<MealPlanDto> assignRecipeToDay(
            @PathVariable Long recipeId,
            @PathVariable DayOfWeek day,
            @PathVariable MealType mealType) {
        return ResponseEntity.ok(mealPlanService.assignRecipeToDay(recipeId, day, mealType));
    }

    @GetMapping("/day/{day}")
    public ResponseEntity<List<MealPlanDto>> getRecipesForDay(@PathVariable DayOfWeek day) {
        return ResponseEntity.ok(mealPlanService.getRecipesForDay(day));
    }

    @GetMapping("/week")
    public ResponseEntity<List<MealPlanDto>> getRecipesForWeek() {
        return ResponseEntity.ok(mealPlanService.getRecipesForWeek());
    }

    @DeleteMapping("/{day}/{mealType}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeRecipeFromDay(
            @PathVariable DayOfWeek day,
            @PathVariable MealType mealType) {
        mealPlanService.removeRecipeFromDay(day, mealType);
    }
}
