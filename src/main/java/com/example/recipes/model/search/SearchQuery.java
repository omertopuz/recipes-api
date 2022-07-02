package com.example.recipes.model.search;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;

@Getter
@Setter
@Builder
public class SearchQuery {
    private List<SearchFilter> searchFilters;
    @Schema(description = "page number", example = "0")
    private int pageNumber;
    @Schema(description = "page size", example = "10")
    private int pageSize;
    @JsonIgnore
    private SortOrder sortOrder;
    private List<JoinColumnProps> joinColumnProps;
}
