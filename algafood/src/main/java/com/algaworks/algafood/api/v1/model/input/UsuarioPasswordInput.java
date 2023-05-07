package com.algaworks.algafood.api.v1.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class UsuarioPasswordInput {
    @ApiModelProperty(example = "123", required = true)
    @NotBlank
    private String senhaAtual;

    @ApiModelProperty(example = "xyz", required = true)
    @NotBlank
    private String novaSenha;

}
