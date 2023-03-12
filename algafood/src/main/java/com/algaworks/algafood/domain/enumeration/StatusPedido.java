package com.algaworks.algafood.domain.enumeration;

public enum StatusPedido {
    CRIADO("criado"),
    CONFIRMADO("confirmado"),
    ENTREGUE("entregue"),
    CANCELADO("cancelado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }

}
