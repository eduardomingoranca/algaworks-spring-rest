package com.algaworks.algafood.api.v2.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class CozinhaInputVersionTwo {
    @NotBlank
    private String nomeCozinha;

}
