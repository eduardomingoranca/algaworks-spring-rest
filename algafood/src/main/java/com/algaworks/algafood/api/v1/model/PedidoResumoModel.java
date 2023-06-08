package com.algaworks.algafood.api.v1.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Relation(collectionRelation = "pedidos")
@Setter
@Getter
public class PedidoResumoModel extends RepresentationModel<PedidoResumoModel> {
    private String codigo;

    private BigDecimal subtotal;

    private BigDecimal taxaFrete;

    private BigDecimal valorTotal;

    private String status;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime dataCriacao;

    private RestauranteApenasNomeModel restaurante;

    private UsuarioModel cliente;

}
