package com.algaworks.algafood.domain.exception;

import static java.lang.String.format;

public class RestauranteNaoEncontradoException extends EntidadeNaoEncontradaException{
    public RestauranteNaoEncontradoException(String message) {
        super(message);
    }

    public RestauranteNaoEncontradoException(Long id) {
        this(format("Nao existe cadastro de restaurante com codigo %d", id));
    }
}
