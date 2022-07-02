package com.example.recipes.model.search;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchFilter {
    private String columnName;
    private QueryOperator operator;
    private Object value;
}
