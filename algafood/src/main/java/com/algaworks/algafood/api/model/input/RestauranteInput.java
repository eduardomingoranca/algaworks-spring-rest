package com.algaworks.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Setter
@Getter
public class RestauranteInput {

    @ApiModelProperty(example = "1", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "10.00", required = true)
    @NotNull
    @PositiveOrZero
    private BigDecimal taxaFrete;

    @Valid
    @NotNull
    private CozinhaIDInput cozinha;

    @Valid
    @NotNull
    private EnderecoInput endereco;

}
