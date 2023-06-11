package com.algaworks.algafood.api.v1.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
    private String codigo;

    @Schema(example = "298.90")
    private BigDecimal subtotal;

    @Schema(example = "10.00")
    private BigDecimal taxaFrete;

    @Schema(example = "308.90")
    private BigDecimal valorTotal;


    @Schema(example = "2022-12-01T20:34:04Z")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime dataCriacao;

    @Schema(example = "2022-12-01T20:35:10Z")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime dataConfirmacao;

    @Schema(example = "2022-12-01T20:35:00Z")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime dataCancelamento;

    @Schema(example = "2022-12-01T20:55:30Z")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime dataEntrega;

    @Schema(example = "CRIADO")
    private String status;

    private FormaPagamentoModel formaPagamento;

    private RestauranteApenasNomeModel restaurante;

    private UsuarioModel cliente;

    private EnderecoModel enderecoEntrega;

    private List<ItemPedidoModel> itens;

}
