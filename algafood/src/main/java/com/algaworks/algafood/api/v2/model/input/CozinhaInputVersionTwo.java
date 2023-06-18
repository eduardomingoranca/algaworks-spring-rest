package com.algaworks.algafood.api.v2.model.input;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class CozinhaInputVersionTwo {
    @NotBlank
    private String nomeCozinha;

}
