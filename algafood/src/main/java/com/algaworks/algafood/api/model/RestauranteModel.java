package com.algaworks.algafood.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

// classe de modelo que representacao de recurso DTO
@Setter
@Getter
public class RestauranteModel {
    private Long id;

    private String nome;

    private BigDecimal precoFrete;

    private CozinhaModel cozinha;

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
