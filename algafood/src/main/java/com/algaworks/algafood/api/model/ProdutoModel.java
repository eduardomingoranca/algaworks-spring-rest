package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProdutoModel {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Porco com molho agridoce")
    private String nome;

    @ApiModelProperty(example = "Deliciosa carne suina ao molho especial")
    private String descricao;

    @ApiModelProperty(example = "78.90")
    private BigDecimal preco;

    @ApiModelProperty(example = "false")
    private Boolean ativo;

}
