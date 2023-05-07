package com.algaworks.algafood.api.v2.model;

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
public class CozinhaModelVersionTwo extends RepresentationModel<CozinhaModelVersionTwo> {

    @ApiModelProperty(example = "1")
    private Long idCozinha;

    @ApiModelProperty(example = "Tailandesa")
    private String nomeCozinha;

}
