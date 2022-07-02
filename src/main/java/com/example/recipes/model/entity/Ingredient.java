package com.example.recipes.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.*;

@Schema(name = "ingredient")
@Entity
@Table(name = "ingredients")
@Data
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long ingredientId;

    @Schema(description = "description of ingredient", example = "crumbled feta cheese")
    private String description;
    @Schema(description = "quantity of ingredient", example = "1 cup")
    private String quantity;
}
