package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.CozinhaModel;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteBasicoModelOpenAPI {
    private Long id;

    private String nome;

    private BigDecimal taxaFrete;

    private CozinhaModel cozinha;

}
