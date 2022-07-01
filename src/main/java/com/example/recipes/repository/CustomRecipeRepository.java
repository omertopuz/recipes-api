package com.example.recipes.repository;


import com.example.recipes.config.SqlFunctionsRegistry;
import com.example.recipes.exceptions.RecipeApiException;
import com.example.recipes.model.entity.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Component
@RequiredArgsConstructor
public class CustomRecipeRepository {

    private final RecipeRepository recipeRepository;

    public List<Recipe> getQueryResult(List<Filter> filters) {
        if (filters.size() > 0) {
            return recipeRepository.findAll(getSpecificationFromFilters(filters));
        } else {
            return recipeRepository.findAll();
        }
    }

    private Specification<Recipe> getSpecificationFromFilters(List<Filter> filter) {
        Specification<Recipe> specification = where(
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.literal(1)
                        ,criteriaBuilder.literal(1)));    // initial condition set up where 1=1
        for (Filter input : filter) {
            specification = specification.and(createSpecification(input));
        }
        return specification;
    }

    private Specification<Recipe> createSpecification(Filter input) {
        switch (input.getOperator()) {
            case EQUALS:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get(input.getField()),
                                castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case NOT_EQ:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.notEqual(root.get(input.getField()),
                                castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case GREATER_THAN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.gt(root.get(input.getField()),
                                (Number) castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case LESS_THAN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.lt(root.get(input.getField()),
                                (Number) castToRequiredType(root.get(input.getField()).getJavaType(), input.getValue()));
            case LIKE:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.like(root.get(input.getField()), "%" + input.getValue() + "%");
            case IN:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.in(root.get(input.getField()))
                                .value(castToRequiredType(root.get(input.getField()).getJavaType(), input.getValues()));
            case FULL_TEXT_SEARCH:
                return (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(criteriaBuilder
                                .function(SqlFunctionsRegistry.FUNCTION_FULL_TEXT_SEARCH
                                        ,String.class
                                        ,root.get(input.getField()),criteriaBuilder.literal(input.getValue())),true);
            default:
                throw new RecipeApiException("Operation not supported yet");
        }
    }

    private Object castToRequiredType(Class fieldType, String value) {
        if (fieldType.isAssignableFrom(Double.class)) {
            return Double.valueOf(value);
        } else if (fieldType.isAssignableFrom(Integer.class)) {
            return Integer.valueOf(value);
        } else if (Enum.class.isAssignableFrom(fieldType)) {
            return Enum.valueOf(fieldType, value);
        }
        return null;
    }

    private Object castToRequiredType(Class fieldType, List<String> value) {
        List<Object> lists = new ArrayList<>();
        for (String s : value) {
            lists.add(castToRequiredType(fieldType, s));
        }
        return lists;
    }
}