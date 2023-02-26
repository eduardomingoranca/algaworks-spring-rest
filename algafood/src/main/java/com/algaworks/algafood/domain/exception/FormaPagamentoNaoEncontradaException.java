package com.algaworks.algafood.domain.exception;

import static java.lang.String.format;

public class FormaPagamentoNaoEncontradaException extends EntidadeNaoEncontradaException {
    public FormaPagamentoNaoEncontradaException(String message) {
        super(message);
    }

    public FormaPagamentoNaoEncontradaException(Long id) {
        this(format("Nao existe forma de pagamento com codigo %d", id));
    }

}
