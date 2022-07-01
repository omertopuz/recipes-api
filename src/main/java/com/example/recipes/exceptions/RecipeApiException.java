package com.example.recipes.exceptions;

public class RecipeApiException extends RuntimeException{

    public RecipeApiException() {
    }

    public RecipeApiException(String message) {
        super(message);
    }
}
