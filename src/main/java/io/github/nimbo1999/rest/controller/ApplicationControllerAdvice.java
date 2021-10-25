package io.github.nimbo1999.rest.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.nimbo1999.exception.PedidoNaoEncontradoException;
import io.github.nimbo1999.exception.RegraNegocioException;
import io.github.nimbo1999.rest.ApiErrors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ApplicationControllerAdvice {
    
    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(code = BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException exeption) {
        return new ApiErrors(exeption.getMessage());
    }

    @ExceptionHandler(PedidoNaoEncontradoException.class)
    @ResponseStatus(code = NOT_FOUND)
    public ApiErrors handlePedidoNaoEncontradoException(PedidoNaoEncontradoException exeption) {
        return new ApiErrors(exeption.getMessage());
    }
}
