package com.algaworks.algafood.api.v1.model.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PedidoInput {
    @Valid
    @NotNull
    private RestauranteIDInput restaurante;

    @Valid
    @NotNull
    private EnderecoInput enderecoEntrega;

    @Valid
    @NotNull
    private FormaPagamentoIDInput formaPagamento;

    @Valid
    @Size(min = 1)
    @NotNull
    private List<ItemPedidoInput> itens;

}
