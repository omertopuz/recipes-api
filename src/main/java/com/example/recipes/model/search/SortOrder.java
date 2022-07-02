package com.example.recipes.model.search;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SortOrder {

    private List<String> ascendingOrder;

    private List<String> descendingOrder;
}
