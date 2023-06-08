package com.algaworks.algafood.api.v1.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "enderecos")
@Setter
@Getter
public class EnderecoModel extends RepresentationModel<EnderecoModel> {
    private String cep;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    private CidadeResumoModel cidade;

}
