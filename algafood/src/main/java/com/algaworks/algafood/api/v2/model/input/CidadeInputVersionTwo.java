package com.algaworks.algafood.api.v2.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CidadeInputVersionTwo {
    @NotBlank
    private String nomeCidade;

    @NotNull
    private Long idEstado;

}
