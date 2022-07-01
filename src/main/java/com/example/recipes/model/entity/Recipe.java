package com.example.recipes.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Schema(name = "recipe")
@Entity
@Table(name = "recipes")
@Data
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long recipeId;

    @Schema(description = "Title for the recipe", example = "Pesto Pizza")
    private String title;
    @Schema(description = "Author of the recipe", example = "John Doe")
    private String author;

    @Schema(description = "Dish type", example = "VEGETARIAN",format = "enum")
    @Enumerated(EnumType.STRING)
    private EnumDishType dishType;

    @Schema(description = "Number of servings", example = "4")
    private Integer servings;

    @Schema(description = "instructions and preparation guide", example = "Preheat oven to 450 degrees F (230 degrees C).Spread pesto on pizza crust. Top with tomatoes, bell peppers, olives, red onions, artichoke hearts and feta cheese. Bake for 8 to 10 minutes, or until cheese is melted and browned.")
    private String instructions;

    @Schema(name = "ingredientList")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "recipeId", foreignKey = @ForeignKey(name = "fk_recipe_ingredient"))
    private Set<Ingredient> ingredients;
}
