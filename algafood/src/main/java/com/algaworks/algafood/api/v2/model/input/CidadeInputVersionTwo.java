package com.algaworks.algafood.api.v2.model.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeInputVersionTwo {
    @NotBlank
    private String nomeCidade;

    @NotNull
    private Long idEstado;

}
