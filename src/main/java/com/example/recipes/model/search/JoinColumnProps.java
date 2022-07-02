package com.example.recipes.model.search;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JoinColumnProps {
    private String joinColumnName;
    private SearchFilter searchFilter;
}
