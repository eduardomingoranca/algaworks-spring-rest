package com.algaworks.algafood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Setter
@Getter
public class ItemPedidoInput {
    @ApiModelProperty(example = "1")
    @NotNull
    private Long produtoId;

    @ApiModelProperty(example = "3")
    @NotNull
    @PositiveOrZero
    private Integer quantidade;

    @ApiModelProperty(example = "Sem pimenta")
    private String observacao;

}
