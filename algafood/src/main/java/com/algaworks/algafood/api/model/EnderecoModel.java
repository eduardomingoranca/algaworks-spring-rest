package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "enderecos")
@Setter
@Getter
public class EnderecoModel extends RepresentationModel<EnderecoModel> {
    @ApiModelProperty(example = "38400-222")
    private String cep;

    @ApiModelProperty(example = "Rua Natal")
    private String logradouro;

    @ApiModelProperty(example = "200")
    private String numero;

    @ApiModelProperty(example = "Casa 01")
    private String complemento;

    @ApiModelProperty(example = "Brasil")
    private String bairro;

    private CidadeResumoModel cidade;

}
