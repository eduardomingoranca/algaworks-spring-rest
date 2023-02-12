package com.algaworks.algafood.domain.exception;

import static java.lang.String.format;

public class CozinhaNaoEncontradaException extends EntidadeNaoEncontradaException {
    public CozinhaNaoEncontradaException(String message) {
        super(message);
    }

    public CozinhaNaoEncontradaException(Long id) {
        this(format("Nao existe um cadastro de cozinha com codigo %d", id));
    }
}
