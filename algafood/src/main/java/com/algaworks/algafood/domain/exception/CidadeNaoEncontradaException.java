package com.algaworks.algafood.domain.exception;

import static java.lang.String.format;

public class CidadeNaoEncontradaException extends EntidadeNaoEncontradaException{

    public CidadeNaoEncontradaException(String message) {
        super(message);
    }

    public CidadeNaoEncontradaException(Long id) {
        this(format("Nao existe um cadastro de cidade com codigo %d", id));
    }
}
