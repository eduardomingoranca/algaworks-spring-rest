package com.algaworks.algafood.domain.exception;

import static java.lang.String.format;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public PedidoNaoEncontradoException(String message) {
        super(message);
    }

    public PedidoNaoEncontradoException(Long id) {
        this(format("Nao existe cadastro de pedido com codigo %d", id));
    }
}
