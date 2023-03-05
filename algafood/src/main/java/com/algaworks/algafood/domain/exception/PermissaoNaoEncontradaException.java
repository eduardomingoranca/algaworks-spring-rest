package com.algaworks.algafood.domain.exception;

import static java.lang.String.format;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {
    public PermissaoNaoEncontradaException(String message) {
        super(message);
    }

    public PermissaoNaoEncontradaException(Long id) {
        this(format("Nao existe permissao com codigo %d", id));
    }

}
