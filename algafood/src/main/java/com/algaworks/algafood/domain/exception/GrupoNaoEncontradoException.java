package com.algaworks.algafood.domain.exception;

import static java.lang.String.format;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public GrupoNaoEncontradoException(String message) {
        super(message);
    }

    public GrupoNaoEncontradoException(Long id) {
        this(format("Nao existe cadastro de grupo com codigo %d", id));
    }

}
