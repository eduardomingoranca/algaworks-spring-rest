package com.algaworks.algafood.api.model;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

// classe de modelo que representacao de recurso DTO
@Setter
@Getter
public class RestauranteModel {
    @ApiModelProperty(example = "1")
    @JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
    private Long id;

    @ApiModelProperty(example = "Thai Gourmet")
    @JsonView({ RestauranteView.Resumo.class, RestauranteView.ApenasNome.class })
    private String nome;

    @ApiModelProperty(example = "10.00")
    @JsonView(RestauranteView.Resumo.class)
    private BigDecimal taxaFrete;

    @JsonView(RestauranteView.Resumo.class)
    private CozinhaModel cozinha;

    private Boolean ativo;

    private Boolean aberto;

    private EnderecoModel endereco;

    /*
     * Origem: cozinha, nome
     * Destino: nome, cozinha
     *
     * o model mapper tem a estrategia de
     * correspondencia, separando os nomes
     * das propriedades em pedacos [chamados
     * de tokens]
     */
//    private String nomeCozinha;
//    private Long idCozinha;

}
