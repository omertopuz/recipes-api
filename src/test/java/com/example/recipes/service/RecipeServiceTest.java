package com.example.recipes.service;

import com.example.recipes.exceptions.EntityNotFoundException;
import com.example.recipes.model.entity.Recipe;
import com.example.recipes.model.request.RecipeDto;
import com.example.recipes.model.search.QueryOperator;
import com.example.recipes.model.search.SearchFilter;
import com.example.recipes.model.search.SearchQuery;
import com.example.recipes.repository.IngredientsRepository;
import com.example.recipes.repository.RecipeRepository;
import com.example.recipes.util.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;
    @Mock
    private IngredientsRepository ingredientsRepository;

    private ObjectMapper objectMapper;

    private RecipeService recipeService;

    private Recipe recipeEntity;
    private RecipeDto recipeDto;
    private Long recipeId = 100L;

    @BeforeEach
    void setUp(){
        objectMapper = TestUtils.buildObjectMapper();
        recipeDto = TestUtils.buildRecipeDto();
        recipeEntity = objectMapper.convertValue(recipeDto,Recipe.class);
        recipeEntity.setRecipeId(recipeId);

        recipeService = new RecipeService(recipeRepository,ingredientsRepository,objectMapper);
    }

    @Test
    void testCreateRecipe() {
        Mockito.when(recipeRepository.save(ArgumentMatchers.any(Recipe.class))).thenReturn(recipeEntity);

        RecipeDto recipeDto = recipeService.createRecipe(TestUtils.buildRecipeDto());
        assertTrue(recipeDto.getRecipeId() > 0);
    }

    @Test
    void testGetRecipe() {
        Mockito.when(recipeRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(recipeEntity));

        RecipeDto fetchedRecipe = recipeService.getRecipe(recipeId);
        assertEquals(fetchedRecipe.getRecipeId(), recipeId);
    }

    @Test
    void testGetRecipeThrownExceptionForNonExistingEntity() {
        Mockito.when(recipeRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> recipeService.getRecipe(recipeId));
        assertTrue(exception.getMessage().contains(recipeId + ""));
    }

    @Test
    void testUpdateRecipe() {
        String newAuthor = "Mystical Author";
        recipeEntity.setAuthor(newAuthor);
        Mockito.when(recipeRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(recipeEntity));
        Mockito.when(recipeRepository.save(ArgumentMatchers.any(Recipe.class))).thenReturn(recipeEntity);

        RecipeDto updatedRecipe =  recipeService.updateRecipe(recipeDto,recipeId);
        assertEquals(updatedRecipe.getAuthor(), newAuthor);
    }

    @Test
    void testDeleteRecipe() {
        Mockito.when(recipeRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.of(recipeEntity));
        recipeService.deleteRecipe(recipeId);
        Mockito.when(recipeRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> recipeService.getRecipe(recipeId));
        assertTrue(exception.getMessage().contains(recipeId + ""));
    }

    @Test
    void testSearch(){
        Mockito.when(recipeRepository.findAll(ArgumentMatchers.any(Specification.class),ArgumentMatchers.any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(recipeEntity)));
        SearchQuery searchQuery = SearchQuery.builder()
                .pageSize(10)
                .pageNumber(0)
                .searchFilters(List.of(SearchFilter.builder()
                        .columnName("author")
                        .operator(QueryOperator.IN)
                        .value(List.of(recipeEntity.getAuthor()))
                        .build()))
                .build();
        Page<RecipeDto> recipeDtoList = recipeService.searchRecipes(searchQuery);
        assertTrue(recipeDtoList.getSize()>=1);
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