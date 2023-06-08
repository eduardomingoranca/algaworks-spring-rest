package com.algaworks.algafood.api.v1.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Relation(collectionRelation = "pedidos")
@Setter
@Getter
public class PedidoModel extends RepresentationModel<PedidoModel> {
    private String codigo;

    private BigDecimal subtotal;

    private BigDecimal taxaFrete;

    private BigDecimal valorTotal;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime dataCriacao;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime dataConfirmacao;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime dataCancelamento;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime dataEntrega;

    private String status;

    private FormaPagamentoModel formaPagamento;

    private RestauranteApenasNomeModel restaurante;

    private UsuarioModel cliente;

    private EnderecoModel enderecoEntrega;

    private List<ItemPedidoModel> itens;

}
