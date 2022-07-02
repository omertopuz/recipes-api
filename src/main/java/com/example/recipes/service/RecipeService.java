package com.example.recipes.service;

import com.example.recipes.model.entity.Recipe;
import com.example.recipes.model.search.SearchQuery;
import com.example.recipes.repository.RecipeRepository;
import com.example.recipes.util.SpecificationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public Page<Recipe> searchRecipes(SearchQuery searchQuery){
        return recipeRepository.findAll(SpecificationUtil.bySearchQuery(searchQuery)
                , PageRequest.of(searchQuery.getPageNumber()
                        ,searchQuery.getPageSize() == 0 ? 10 : searchQuery.getPageSize())
        );
    }
}
