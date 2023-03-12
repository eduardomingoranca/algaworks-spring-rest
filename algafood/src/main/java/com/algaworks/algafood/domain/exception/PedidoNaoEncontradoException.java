package com.algaworks.algafood.domain.exception;

import static java.lang.String.format;

public class PedidoNaoEncontradoException extends EntidadeNaoEncontradaException{
    public PedidoNaoEncontradoException(String codigo) {
        super(format("Nao existe cadastro de pedido com codigo %s", codigo));
    }

}
