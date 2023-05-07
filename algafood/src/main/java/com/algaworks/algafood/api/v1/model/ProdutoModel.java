package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@Relation(collectionRelation = "produtos")
@Setter
@Getter
public class ProdutoModel extends RepresentationModel<ProdutoModel> {
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
