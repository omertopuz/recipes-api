package com.example.recipes.repository;

import com.example.recipes.model.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "recipes",collectionResourceRel = "recipe")
public interface RecipeRepository extends JpaRepository<Recipe,Long> {

}
