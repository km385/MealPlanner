package com.example.backend.utils;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.backend.entities.Recipe;
import com.example.backend.entities.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityUtils {
    
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Not authenticated");
        }
        return (User) authentication.getPrincipal();
    }
    
    public void validateUserAccess(Long userId) {
        User currentUser = getCurrentUser();
        if (!currentUser.getId().equals(userId)) {
            throw new AccessDeniedException("Access denied");
        }
    }
    
    public void validateRecipeAccess(Recipe recipe) {
        User currentUser = getCurrentUser();
        if (!recipe.getUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Access denied");
        }
    }
}
