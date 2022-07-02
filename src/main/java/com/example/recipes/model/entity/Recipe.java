package com.example.recipes.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "recipes")
@Getter
@Setter
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    private String title;
    private String author;

    @Enumerated(EnumType.STRING)
    private EnumDishType dishType;

    private Integer servings;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "recipe",fetch = FetchType.EAGER)
    private Set<Ingredient> ingredients;
}
