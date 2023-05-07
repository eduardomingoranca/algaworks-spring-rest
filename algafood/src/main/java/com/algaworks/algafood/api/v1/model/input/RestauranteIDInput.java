package com.algaworks.algafood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class RestauranteIDInput {
    @ApiModelProperty(example = "1")
    @NotNull
    private Long id;

}
