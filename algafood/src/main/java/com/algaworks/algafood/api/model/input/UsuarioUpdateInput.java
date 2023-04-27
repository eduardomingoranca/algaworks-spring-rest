package com.algaworks.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class UsuarioUpdateInput {
    @ApiModelProperty(example = "Kevin Ferrer", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "kevin_ferrer@algafood.com.br", required = true)
    @Email
    @NotBlank
    private String email;

}
