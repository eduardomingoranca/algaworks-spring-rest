package com.algaworks.algafood.api.v1.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Setter
@Getter
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel> {
    private Long produtoId;

    private String produtoNome;

    private Long quantidade;

    private BigDecimal precoUnitario;

    private BigDecimal precoTotal;

    private String observacao;

}
