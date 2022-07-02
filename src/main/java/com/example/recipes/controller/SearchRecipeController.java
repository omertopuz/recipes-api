package com.example.recipes.controller;

import com.example.recipes.model.entity.Recipe;
import com.example.recipes.model.search.SearchQuery;
import com.example.recipes.service.RecipeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "recipes")
@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class SearchRecipeController {

    private final RecipeService recipeService;

    @PostMapping("/search")
    public Page<Recipe> searchRecipe(@RequestBody SearchQuery searchRecipeRequest){
        return recipeService.searchRecipes(searchRecipeRequest);
    }

}
