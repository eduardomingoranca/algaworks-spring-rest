package com.algaworks.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;


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
