package com.example.recipes.repository;

import com.example.recipes.model.entity.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomRecipeRepositoryTest {

    @Autowired
    private CustomRecipeRepository customRecipeRepository;

    @Test
    void testDynamicSpecification() {
        Filter titleLike = Filter.builder()
                .field("title")
                .operator(QueryOperator.LIKE)
                .value("pizza")
                .build();
        Filter servingsEquals = Filter.builder()
                .field("servings")
                .operator(QueryOperator.EQUALS)
                .value("4")
                .build();
        List<Filter> filters = new ArrayList<>();
        filters.add(titleLike);
        filters.add(servingsEquals);
        List<Recipe> recipes = customRecipeRepository.getQueryResult(filters);
        assertEquals(0, recipes.size());

    }
}