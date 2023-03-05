package com.algaworks.algafood.domain.exception;

import static java.lang.String.format;

public class ProdutoNaoEncontradoException extends EntidadeNaoEncontradaException {

    public ProdutoNaoEncontradoException(String message) {
        super(message);
    }

    public ProdutoNaoEncontradoException(Long id, Long produtoID) {
        this(format("Nao existe um cadastro de produto com codigo %d para o restaurante de codigo %d", produtoID, id));
    }

}
