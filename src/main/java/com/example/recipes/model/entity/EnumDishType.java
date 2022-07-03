package com.example.recipes.model.entity;

//public enum EnumDishType {
//    VEGETARIAN,
//    VEGAN,
//    NO_SENSE
//}

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EnumDishType {
    VEGETARIAN("VEGETARIAN"),

    VEGAN("VEGAN"),

    NO_SENSE("NO_SENSE");


    private final String value;

    EnumDishType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @JsonCreator
    public static EnumDishType fromValue(String value) {
        for (EnumDishType b : EnumDishType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
