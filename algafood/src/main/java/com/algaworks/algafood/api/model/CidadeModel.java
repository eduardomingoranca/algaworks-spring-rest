package com.algaworks.algafood.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "Representa uma cidade")
@Setter
@Getter
public class CidadeModel {
    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Uberlandia")
    private String nome;

    private EstadoModel estado;
}
