package com.example.recipes.repository;

import com.example.recipes.model.entity.Recipe;
import com.example.recipes.model.search.JoinColumnProps;
import com.example.recipes.model.search.QueryOperator;
import com.example.recipes.model.search.SearchFilter;
import com.example.recipes.model.search.SearchQuery;
import com.example.recipes.util.SpecificationUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class CustomRecipeRepositoryTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Test
    void testSpecificationUtil(){
        SearchFilter filter1 = SearchFilter.builder()
                .columnName("servings")
                .operator(QueryOperator.EQUALS)
                .value(2)
                .build();

        JoinColumnProps joins = JoinColumnProps.builder()
                .joinColumnName("ingredients")
                .searchFilter(SearchFilter.builder()
                        .value("tomatoes")
                        .operator(QueryOperator.EQUALS)
                        .columnName("description")
                        .build())
                .build();

        Specification<Recipe> spec = SpecificationUtil.bySearchQuery(SearchQuery.builder()
                        .joinColumnProps(List.of(joins))
                .searchFilters(List.of(filter1))
                .build())
                ;
        List<Recipe> recipeList = recipeRepository.findAll(spec);
    }

    @Test
    void testSpecificationUtilInOperator(){
        SearchFilter filter1 = SearchFilter.builder()
                .columnName("author")
                .operator(QueryOperator.IN)
                .value(Arrays.asList("john","mary","someone"))
                .build();

        Specification<Recipe> spec = SpecificationUtil.bySearchQuery(SearchQuery.builder()
                .searchFilters(List.of(filter1))
                .build())
                ;
        List<Recipe> recipeList = recipeRepository.findAll(spec);
    }

    @Test
    void testFtsWithSpecificationUtilWithFts(){
        SearchFilter filter1 = SearchFilter.builder()
                .columnName("instructions")
                .operator(QueryOperator.FULL_TEXT_SEARCH)
                .value("oven DEGREE tomatoes pepper")
                .build();

        Specification<Recipe> spec = SpecificationUtil.bySearchQuery(SearchQuery.builder()
                .searchFilters(List.of(filter1))
                .build())
                ;
        List<Recipe> recipeList = recipeRepository.findAll(spec);
    }
}