package com.algaworks.algafood.api.v1.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cozinhas")
@ApiModel(description = "Representa uma cozinha")
@Setter
@Getter
public class CozinhaModel extends RepresentationModel<CozinhaModel> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Tailandesa")
    private String nome;

}
