package com.algaworks.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class UsuarioInput {
    @Schema(example = "John Smith")
    @NotBlank
    private String nome;

    @Schema(example = "john.ger@algafood.com.br")
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String senha;

}
