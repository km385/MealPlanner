package com.example.backend.exceptions;

import com.example.backend.entities.DayOfWeek;

public class MealForTheDayNotFound extends RuntimeException {
    public MealForTheDayNotFound(Long userId, DayOfWeek dayOfWeek) {
        super("Meal for the day not found for user with id: " + userId + " and day of week: " + dayOfWeek);
    }
}
