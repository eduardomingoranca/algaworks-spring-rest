package com.algaworks.algafood.api.model;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "Representa uma cozinha")
@Setter
@Getter
public class CozinhaModel {

    @ApiModelProperty(example = "1")
    @JsonView(RestauranteView.Resumo.class)
    private Long id;

    /*
     * Origem: cozinha, nome
     * Destino: cozinha, cozinha, nome
     */
    // private String cozinhaNome;
    @ApiModelProperty(example = "Tailandesa")
    @JsonView(RestauranteView.Resumo.class)
    private String nome;

}
