package com.example.recipes.model.search;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JoinColumnProps {
    @Schema(description = "child entity property name", example = "description")
    private String joinColumnName;
    private SearchFilter searchFilter;
}
