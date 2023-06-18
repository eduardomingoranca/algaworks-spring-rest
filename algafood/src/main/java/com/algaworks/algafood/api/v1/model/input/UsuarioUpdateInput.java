package com.algaworks.algafood.api.v1.model.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioUpdateInput {
    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;

}
