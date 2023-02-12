package com.algaworks.algafood.domain.exception;

import static java.lang.String.format;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {
    public EstadoNaoEncontradoException(String message) {
        super(message);
    }

    public EstadoNaoEncontradoException(Long id) {
//        this() esta chamando o constructor acima
        this(format("Nao existe um cadastro de estado com codigo %d", id));
    }

}
