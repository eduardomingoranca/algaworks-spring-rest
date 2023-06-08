package com.algaworks.algafood.api.v2.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cozinhas")
@Setter
@Getter
public class CozinhaModelVersionTwo extends RepresentationModel<CozinhaModelVersionTwo> {
    private Long idCozinha;

    private String nomeCozinha;

}
