package com.algaworks.algafood.client.algafood_java_client.client.model.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RestauranteResumoModel {

    private Long id;

    private String nome;

    private BigDecimal taxaFrete;

    private CozinhaModel cozinha;

}
