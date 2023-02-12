package com.algaworks.algafood.domain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

// ao transformar a classe em abstract nao pode instancia-la mais
@ResponseStatus(value = NOT_FOUND)
public abstract class EntidadeNaoEncontradaException extends NegocioException {

    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }

}
