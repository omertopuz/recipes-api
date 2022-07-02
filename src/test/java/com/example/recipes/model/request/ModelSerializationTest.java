package com.example.recipes.model.request;

import com.example.recipes.model.entity.Recipe;
import com.example.recipes.util.TestUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ModelSerializationTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        objectMapper = TestUtils.buildObjectMapper();
    }

    @Test
    void should_build_recipe_dto_when_json_str_given() throws JsonProcessingException {
        String requestJson = "{\n" +
                "  \"recipeId\": 0,\n" +
                "  \"title\": \"Pesto Pizza\",\n" +
                "  \"author\": \"John Doe\",\n" +
                "  \"dishType\": \"VEGETARIAN\",\n" +
                "  \"servings\": 4,\n" +
                "  \"instructions\": \"Preheat oven to 450 degrees F (230 degrees C).Spread pesto on pizza crust. Top with tomatoes, bell peppers, olives, red onions, artichoke hearts and feta cheese. Bake for 8 to 10 minutes, or until cheese is melted and browned.\",\n" +
                "  \"ingredients\": [\n" +
                "    {\n" +
                "      \"ingredientId\": 0,\n" +
                "      \"description\": \"crumbled feta cheese\",\n" +
                "      \"quantity\": \"1 cup\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        RecipeDto recipeDto = objectMapper.readValue(requestJson,RecipeDto.class);
        assertNotNull(recipeDto);
        assertNotNull(recipeDto.getIngredients());
        assertEquals(recipeDto.getIngredients().size(),1);
    }

    @Test
    void test_convert_object() throws JsonProcessingException {
        String requestJson = "{\n" +
                "  \"recipeId\": 0,\n" +
                "  \"title\": \"Pesto Pizza\",\n" +
                "  \"author\": \"John Doe\",\n" +
                "  \"dishType\": \"VEGETARIAN\",\n" +
                "  \"servings\": 4,\n" +
                "  \"instructions\": \"Preheat oven to 450 degrees F (230 degrees C).Spread pesto on pizza crust. Top with tomatoes, bell peppers, olives, red onions, artichoke hearts and feta cheese. Bake for 8 to 10 minutes, or until cheese is melted and browned.\",\n" +
                "  \"ingredients\": [\n" +
                "    {\n" +
                "      \"ingredientId\": 0,\n" +
                "      \"description\": \"crumbled feta cheese\",\n" +
                "      \"quantity\": \"1 cup\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        RecipeDto recipeDto = objectMapper.readValue(requestJson,RecipeDto.class);
        assertNotNull(recipeDto);
        assertNotNull(recipeDto.getIngredients());
        assertEquals(recipeDto.getIngredients().size(),1);

        Recipe recipe = objectMapper.convertValue(recipeDto,Recipe.class);
    }

}