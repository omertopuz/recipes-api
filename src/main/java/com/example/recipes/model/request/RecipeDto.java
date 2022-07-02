package com.example.recipes.model.request;

import com.example.recipes.model.entity.EnumDishType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RecipeDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long recipeId;

    @Schema(description = "Title for the recipe", example = "Pesto Pizza")
    private String title;
    @Schema(description = "Author of the recipe", example = "John Doe")
    private String author;

    @Schema(description = "Dish type", example = "VEGETARIAN",format = "enum")
    private EnumDishType dishType;

    @Schema(description = "Number of servings", example = "4")
    private Integer servings;

    @Schema(description = "instructions and preparation guide", example = "Preheat oven to 450 degrees F (230 degrees C).Spread pesto on pizza crust. Top with tomatoes, bell peppers, olives, red onions, artichoke hearts and feta cheese. Bake for 8 to 10 minutes, or until cheese is melted and browned.")
    private String instructions;

    @Schema(name = "ingredients")
    private List<IngredientDto> ingredients;
}
