package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Setter
@Getter
public class ItemPedidoModel extends RepresentationModel<ItemPedidoModel> {
    @ApiModelProperty(example = "2")
    private Long produtoId;

    @ApiModelProperty(example = "Camarao tailandes")
    private String produtoNome;

    @ApiModelProperty(example = "1")
    private Long quantidade;

    @ApiModelProperty(example = "110.00")
    private BigDecimal precoUnitario;

    @ApiModelProperty(example = "110.00")
    private BigDecimal precoTotal;

    @ApiModelProperty(example = "Camarao Tailandes")
    private String observacao;

}
