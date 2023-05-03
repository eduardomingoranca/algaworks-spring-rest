package com.algaworks.algafood.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(example = "b5741512-5a5e-4768-9ac1-8aabefe58dc2")
    private String codigo;

    @ApiModelProperty(example = "110.00")
    private BigDecimal subtotal;

    @ApiModelProperty(example = "10.00")
    private BigDecimal taxaFrete;

    @ApiModelProperty(example = "120.00")
    private BigDecimal valorTotal;

    @ApiModelProperty(example = "2023-10-30 21:10:00")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime dataCriacao;

    @ApiModelProperty(example = "2023-10-30 21:10:45")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime dataConfirmacao;

    @ApiModelProperty(example = "2023-10-30 21:55:44")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime dataCancelamento;

    @ApiModelProperty(example = "2023-10-30 21:55:44")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime dataEntrega;

    @ApiModelProperty(example = "ENTREGUE")
    private String status;

    private FormaPagamentoModel formaPagamento;

    private RestauranteResumoModel restaurante;

    private UsuarioModel cliente;

    private EnderecoModel enderecoEntrega;

    private List<ItemPedidoModel> itens;

}
