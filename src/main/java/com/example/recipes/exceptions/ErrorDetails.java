package com.example.recipes.exceptions;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Data
@Builder
@Schema(name = "ErrorDetails", description = "Error Details")
public class ErrorDetails {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Schema(name = "time", description = "error date time")
    private OffsetDateTime time;

    @Schema(name = "message", example = "An error occurred", description = "error message")
    private String message;

    /**
     * Exception Type
     */
    public enum ExceptionTypeEnum {
        EXCEPTION("Exception"),

        APIEXCEPTION("ApiException"),

        NOTFOUNDEXCEPTION("NotFoundException");


        private final String value;

        ExceptionTypeEnum(String value) {
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
        public static ExceptionTypeEnum fromValue(String value) {
            for (ExceptionTypeEnum b : ExceptionTypeEnum.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }

    @Schema(name = "exceptionType", example = "ApiException", description = "Exception Type")
    private ExceptionTypeEnum exceptionType;

}
