package io.github.nimbo1999.rest;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

public class ApiErrors {
    @Getter
    public List<String> errors;

    public ApiErrors(String ErrorMessage) {
        this.errors = Arrays.asList(ErrorMessage);
    }
}
