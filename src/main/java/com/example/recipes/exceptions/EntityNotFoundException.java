package com.example.recipes.exceptions;

public class EntityNotFoundException extends RecipeApiException{
    public EntityNotFoundException(String message)
    {
        super(message);
    }
}
