package com.algaworks.algafood.domain.exception;

import static java.lang.String.format;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {
    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }

    public UsuarioNaoEncontradoException(Long id) {
        this(format("Nao existe cadastro de usuario com codigo %d", id));
    }

}
