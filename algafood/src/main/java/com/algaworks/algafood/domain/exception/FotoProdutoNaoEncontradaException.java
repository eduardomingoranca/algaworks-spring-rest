package com.algaworks.algafood.domain.exception;

import static java.lang.String.format;

public class FotoProdutoNaoEncontradaException extends EntidadeNaoEncontradaException{

    public FotoProdutoNaoEncontradaException(String message) {
        super(message);
    }

    public FotoProdutoNaoEncontradaException(Long restauranteId, Long produtoId) {
        this(format("Nao existe um cadastro de foto do produto com codigo %d para o restaurante de codigo %d", produtoId, restauranteId));
    }

}
