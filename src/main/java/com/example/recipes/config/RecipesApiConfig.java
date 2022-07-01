package com.example.recipes.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Component;

@Component
@OpenAPIDefinition(
        info = @Info(title = "Recipe API", version = "v1", description = "Recipe API allows users to manage their favourite recipes by adding, updating, removing and fetching recipes. Additionally users are be able to filter available recipes based on dish type, number of servings, include/exclude ingredients, full text search on instructions"),
        tags = {
                @Tag(name = "recipes", description = "initiate a communication, get details of communication, update status of communication, listing and querying communications")
        },
        servers = {
                @Server(description = "Recipes API - local", url = "http://localhost:${server.port}"),
        }
        )
public class RecipesApiConfig {
}
