package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import com.example.backend.entities.DayOfWeek;
import com.example.backend.entities.MealPlan;
import com.example.backend.entities.MealType;

@Repository
public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {
    List<MealPlan> findByUserIdOrderByDayOfWeek(Long userId);
    List<MealPlan> findByUserIdAndDayOfWeek(Long userId, DayOfWeek dayOfWeek);
    Optional<MealPlan> findByUserIdAndDayOfWeekAndMealType(Long userId, DayOfWeek dayOfWeek, MealType mealType);
    void deleteByUserIdAndDayOfWeekAndMealType(Long userId, DayOfWeek dayOfWeek, MealType mealType);
}
