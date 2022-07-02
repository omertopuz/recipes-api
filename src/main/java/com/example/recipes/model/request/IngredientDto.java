package com.example.recipes.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long ingredientId;

    @Schema(description = "description of ingredient", example = "crumbled feta cheese")
    private String description;
    @Schema(description = "quantity of ingredient", example = "1 cup")
    private String quantity;
}
