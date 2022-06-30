package com.example.recipes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(value = {RecipeApiException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDetails apiException(RecipeApiException ex) {
        return ErrorDetails.builder()
                .exceptionType(ErrorDetails.ExceptionTypeEnum.APIEXCEPTION)
                .message(ex.getMessage())
                .time(OffsetDateTime.now())
                .build();
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorDetails unKnownException(Exception ex) {
        return ErrorDetails.builder()
                .exceptionType(ErrorDetails.ExceptionTypeEnum.EXCEPTION)
                .message("Unexpected Error occurred: "+ex.getMessage())
                .time(OffsetDateTime.now())
                .build();
    }

}
