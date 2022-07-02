package com.example.recipes.controller;

import com.example.recipes.exceptions.ErrorDetails;
import com.example.recipes.model.request.RecipeDto;
import com.example.recipes.model.search.SearchQuery;
import com.example.recipes.service.RecipeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "recipes")
@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipesController {

    private final RecipeService recipeService;

    @Operation(
            operationId = "createRecipe",
            summary = "create a new recipe",
            tags = { "recipes" },
            responses = {
                    @ApiResponse(responseCode = "201", description = "new recipe created", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "invalid input, object invalid", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetails.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity<RecipeDto> createRecipe(
            @Parameter(name = "RecipeDto") @RequestBody(required = false) RecipeDto recipeDto
    ){
        return new ResponseEntity<>(recipeService.createRecipe(recipeDto), HttpStatus.CREATED);
    }

    @Operation(
            operationId = "getAllRecipes",
            summary = "get All Recipes",
            tags = { "recipes" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "recipes fetched")
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "",
            produces = { "application/json" }
    )
    public ResponseEntity<Page<RecipeDto>> getAllRecipes(
            @Parameter(name = "pageSize",example = "10") @RequestParam(required = false) Integer pageSize
            ,@Parameter(name = "pageNumber",example = "0") @RequestParam(required = false) Integer pageNumber
    ){
        return new ResponseEntity<>(recipeService.getAllRecipes(pageSize,pageNumber), HttpStatus.OK);
    }

    @Operation(
            operationId = "getRecipe",
            summary = "get Recipe previously created",
            tags = { "recipes" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "recipe fetched", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDto.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/{recipeId}",
            produces = { "application/json" }
    )
    public ResponseEntity<RecipeDto> getRecipe(
            @Parameter(name = "recipeId", description = "recipe id", required = true) @PathVariable("recipeId") Long recipeId
    ){
        return new ResponseEntity<>(recipeService.getRecipe(recipeId), HttpStatus.OK);
    }

    @Operation(
            operationId = "updateRecipe",
            summary = "update Recipe",
            tags = { "recipes" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "recipe updated", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = RecipeDto.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/{recipeId}",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity<RecipeDto> updateRecipe(
            @Parameter(name = "recipeId", description = "recipe id", required = true) @PathVariable("recipeId") Long recipeId
            ,@Parameter(name = "RecipeDto") @RequestBody(required = false) RecipeDto recipeDto
    ){
        return new ResponseEntity<>(recipeService.updateRecipe(recipeDto,recipeId), HttpStatus.OK);
    }

    @Operation(
            operationId = "deleteRecipe",
            summary = "delete Recipe",
            tags = { "recipes" },
            responses = {
                    @ApiResponse(responseCode = "200", description = "recipe deleted")
            }
    )
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/{recipeId}",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity deleteRecipe(
            @Parameter(name = "recipeId", description = "recipe id", required = true) @PathVariable("recipeId") Long recipeId
    ){
        recipeService.deleteRecipe(recipeId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Operation(
            operationId = "searchRecipe",
            summary = "search Recipe",
            tags = { "recipes" },
            responses = {@ApiResponse(responseCode = "200", description = "recipes fetched")}
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/search",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ResponseEntity<Page<RecipeDto>> searchRecipe(@RequestBody SearchQuery searchRecipeRequest){
        return new ResponseEntity<>(recipeService.searchRecipes(searchRecipeRequest), HttpStatus.OK);
    }

}
