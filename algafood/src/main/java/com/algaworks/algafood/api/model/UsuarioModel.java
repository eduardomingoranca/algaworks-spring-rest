package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "usuarios")
@Setter
@Getter
public class UsuarioModel extends RepresentationModel<UsuarioModel> {
    @ApiModelProperty(example = "7")
    private Long id;

    @ApiModelProperty(example = "Charles Lewis")
    private String nome;

    @ApiModelProperty(example = "mail.test.aw+charles@algafood.com")
    private String email;

}
