package com.example.recipes.service;

import com.example.recipes.exceptions.EntityNotFoundException;
import com.example.recipes.model.entity.Ingredient;
import com.example.recipes.model.entity.Recipe;
import com.example.recipes.model.request.IngredientDto;
import com.example.recipes.model.request.RecipeDto;
import com.example.recipes.model.search.SearchQuery;
import com.example.recipes.repository.IngredientsRepository;
import com.example.recipes.repository.RecipeRepository;
import com.example.recipes.util.SpecificationUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientsRepository ingredientsRepository;
    private final ObjectMapper objectMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    public RecipeDto createRecipe(RecipeDto recipeDto){
        Recipe recipeEntity = toEntity(recipeDto);
        for (Ingredient ingredient: recipeEntity.getIngredients()) {
            ingredient.setRecipe(recipeEntity);
        }
        recipeEntity = recipeRepository.save(recipeEntity);

        return toDto(recipeEntity);
    }

    public Page<RecipeDto> getAllRecipes(int pageSize, int pageNumber){
        return recipeRepository.findAll(PageRequest.of(pageNumber
                        ,pageSize == 0 ? 10 : pageSize)
        ).map(this::toDto);
    }

    public RecipeDto getRecipe(Long recipeId){
        return toDto(findRecipeChecked(recipeId));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RecipeDto updateRecipe(RecipeDto recipeDto, Long recipeId){
        findRecipeChecked(recipeId);
        Recipe recipeEntity = toEntity(recipeDto);
        recipeEntity.setRecipeId(recipeId);
        for (Ingredient ingredient: recipeEntity.getIngredients()) {
            ingredient.setRecipe(recipeEntity);
        }
        ingredientsRepository.deleteByRecipeRecipeId(recipeId);
        recipeEntity = recipeRepository.save(recipeEntity);
        return toDto(recipeEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteRecipe(Long recipeId){
        Recipe recipeEntity = findRecipeChecked(recipeId);
        recipeRepository.delete(recipeEntity);
    }

    public Page<RecipeDto> searchRecipes(SearchQuery searchQuery){
        return recipeRepository.findAll(SpecificationUtil.bySearchQuery(searchQuery)
                , PageRequest.of(searchQuery.getPageNumber()
                        ,searchQuery.getPageSize() == 0 ? 10 : searchQuery.getPageSize())
        ).map(this::toDto);
    }

    private Recipe findRecipeChecked(Long recipeId) {
        return recipeRepository.findById(recipeId).orElseThrow(()->new EntityNotFoundException("Could not find recipe with id: " + recipeId));
    }

    private RecipeDto toDto(Recipe recipe){
        Set<Ingredient> ingredientList = recipe.getIngredients();
        recipe.setIngredients(null);
        RecipeDto recipeDto = objectMapper.convertValue(recipe,RecipeDto.class);
        if(ingredientList != null){
            List<IngredientDto> ingredientDtoList = objectMapper.convertValue(ingredientList, new TypeReference<>() { });
            recipeDto.setIngredients(ingredientDtoList);
        }
        return recipeDto;
    }

    private Recipe toEntity(RecipeDto recipeDto){
       return objectMapper.convertValue(recipeDto,Recipe.class);
    }
}
