package com.algaworks.algafood.domain.exception;

// ao transformar a classe em abstract nao pode instancia-la mais
public abstract class EntidadeNaoEncontradaException extends NegocioException {

    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }

}
