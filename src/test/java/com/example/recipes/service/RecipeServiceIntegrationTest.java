package com.example.recipes.service;

import com.example.recipes.exceptions.EntityNotFoundException;
import com.example.recipes.model.request.RecipeDto;
import com.example.recipes.model.search.QueryOperator;
import com.example.recipes.model.search.SearchFilter;
import com.example.recipes.model.search.SearchQuery;
import com.example.recipes.repository.RecipeRepository;
import com.example.recipes.util.TestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecipeServiceIntegrationTest {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    void should_create_recipe() {
        RecipeDto recipeDto = recipeService.createRecipe(TestUtils.buildRecipeDto());
        assertTrue(recipeDto.getRecipeId() > 0);
    }

    @Test
    void should_return_existing_recipe() {
        recipeRepository.findAll(PageRequest.of(0, 1)).get().findFirst()
                .ifPresent(recipe -> {
                    RecipeDto fetchedRecipe = recipeService.getRecipe(recipe.getRecipeId());
                    assertEquals(fetchedRecipe.getRecipeId(), recipe.getRecipeId());
                });
    }

    @Test
    void should_throw_exception_non_existing_recipe() {
        Long entityId = -1L;
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> recipeService.getRecipe(entityId));
        assertTrue(exception.getMessage().contains(entityId + ""));
    }

    @Test
    void should_update_existing_recipe() {
        recipeRepository.findAll(PageRequest.of(0, 1)).get().findFirst()
                .ifPresent(recipe -> {
                    RecipeDto recipeDto = TestUtils.buildRecipeDto();
                    recipeDto.setAuthor(recipeDto.getAuthor() + "**UPDATE");
                    RecipeDto updatedRecipe =  recipeService.updateRecipe(recipeDto,recipe.getRecipeId());
                    assertEquals(updatedRecipe.getAuthor(), recipeDto.getAuthor());
                });
    }

    @Test
    void should_delete_existing_recipe() {
        recipeRepository.findAll(PageRequest.of(0, 1)).get().findFirst()
                .ifPresent(recipe -> {
                    recipeService.deleteRecipe(recipe.getRecipeId());
                    EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> recipeService.getRecipe(recipe.getRecipeId()));
                    assertTrue(exception.getMessage().contains(recipe.getRecipeId() + ""));
                });
    }

    @Test
    void should_throw_exception_when_delete_non_existing_recipe() {
        Long entityId = -1L;
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> recipeService.deleteRecipe(entityId));
        assertTrue(exception.getMessage().contains(entityId + ""));
    }

    @Test
    void should_return_at_least_one_record_with_existing_column_value(){
        recipeRepository.findAll(PageRequest.of(0, 1)).get().findFirst()
                .ifPresent(recipe -> {
                    SearchQuery searchQuery = SearchQuery.builder()
                            .searchFilters(List.of(SearchFilter.builder()
                                    .columnName("author")
                                    .operator(QueryOperator.IN)
                                    .value(List.of(recipe.getAuthor()))
                                    .build()))
                            .build();
                    Page<RecipeDto> recipeDtoList = recipeService.searchRecipes(searchQuery);
                    assertTrue(recipeDtoList.getSize()>=1);
                });
    }
}