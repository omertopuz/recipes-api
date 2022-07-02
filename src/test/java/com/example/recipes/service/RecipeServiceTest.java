package com.example.recipes.service;

import com.example.recipes.model.entity.Recipe;
import com.example.recipes.model.request.RecipeDto;
import com.example.recipes.repository.IngredientsRepository;
import com.example.recipes.repository.RecipeRepository;
import com.example.recipes.util.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class RecipeServiceTest {

    @MockBean
    private RecipeRepository recipeRepository;
    @MockBean
    private IngredientsRepository ingredientsRepository;
    @MockBean
    private ObjectMapper objectMapper;

    private RecipeService recipeService;

    @BeforeEach
    void setUp(){
        objectMapper = TestUtils.buildObjectMapper();
        recipeService = new RecipeService(recipeRepository,ingredientsRepository,objectMapper);
    }

    @Test
    void testToDto() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Recipe recipe = new Recipe();
        recipe.setAuthor("john");
        Method method = RecipeService.class.getDeclaredMethod("toDto", Recipe.class);
        method.setAccessible(true);
        RecipeDto recipeDto = (RecipeDto) method.invoke(recipeService, recipe);
        assertNotNull(recipeDto);
        assertEquals(recipeDto.getAuthor(), recipe.getAuthor());
    }

    @Test
    void testToEntity() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setServings(5);
        Method method = RecipeService.class.getDeclaredMethod("toEntity", RecipeDto.class);
        method.setAccessible(true);
        Recipe recipe = (Recipe) method.invoke(recipeService, recipeDto);
        assertNotNull(recipeDto);
        assertEquals(recipeDto.getServings(), recipe.getServings());
    }
}