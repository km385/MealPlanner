package com.example.backend.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.example.backend.utils.ValidationUtils;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecipeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleRecipeNotFound(RecipeNotFoundException e) {
        return new ErrorResponse("Recipe not found", e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleAccessDenied(AccessDeniedException e) {
        return new ErrorResponse("Access denied", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationErrors(MethodArgumentNotValidException e) {
        return new ErrorResponse("Validation failed",
                ValidationUtils.mapValidationErrors(e.getBindingResult()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleDataIntegrityViolation(DataIntegrityViolationException e) {
        return new ErrorResponse("Database error", "Could not perform operation due to data constraint");
    }

    @ExceptionHandler(MealForTheDayNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleGenerald(Exception e) {
        return new ErrorResponse("Meals not found", e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleBadCredentials(BadCredentialsException e) {
        return new ErrorResponse("Authentication failed", "Invalid username or password");
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAuthentication(AuthenticationException e) {
        return new ErrorResponse("Authentication failed", e.getMessage());
    }

    @ExceptionHandler(RegistrationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRegistration(RegistrationException e) {
        return new ErrorResponse("Registration failed", e.getMessage());
    }

    

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneral(Exception e) {
        return new ErrorResponse("Internal server error", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleEnumTypeException(MethodArgumentTypeMismatchException ex) {
        if (ex.getParameter().getParameter().getType().isEnum()) {
            String enumClass = ex.getParameter().getParameter().getType().getSimpleName();
            String invalidValue = String.valueOf(ex.getValue());
            String message = String.format(
                "Invalid value '%s' for %s. Allowed values are: %s",
                invalidValue,
                enumClass,
                Arrays.toString(ex.getParameter().getParameter().getType().getEnumConstants())
            );
            return new ErrorResponse("Invalid enum value", message);
        }
        return new ErrorResponse("Bad request", ex.getMessage());
    }
}