package com.example.recipes.model.search;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SearchQuery {
    private List<SearchFilter> searchFilters;
    private int pageNumber;
    private int pageSize;
    private SortOrder sortOrder;
    private List<JoinColumnProps> joinColumnProps;
}
