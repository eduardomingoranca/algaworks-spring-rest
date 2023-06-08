package com.algaworks.algafood.api.v1.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class RestauranteIDInput {
    @NotNull
    private Long id;

}
