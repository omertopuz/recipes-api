package com.example.recipes.repository;

import com.example.recipes.model.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientsRepository extends JpaRepository<Ingredient,Long> {
}
