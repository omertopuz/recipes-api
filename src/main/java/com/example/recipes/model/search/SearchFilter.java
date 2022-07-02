package com.example.recipes.model.search;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SearchFilter {
    @Schema(description = "entity property name", example = "instructions")
    private String columnName;
    @Schema(description = "operator", example = "EQUALS", format = "enum")
    private QueryOperator operator;
    @Schema(description = "value of the filter. Use arrays for IN operator", example = "5")
    private Object value;
}
