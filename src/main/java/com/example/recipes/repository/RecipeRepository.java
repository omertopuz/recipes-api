package com.example.recipes.repository;

import com.example.recipes.model.entity.Recipe;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Tag(name = "recipes")
@RepositoryRestResource(path = "recipes",collectionResourceRel = "recipe")
public interface RecipeRepository extends JpaRepository<Recipe,Long>, JpaSpecificationExecutor<Recipe> {

}
