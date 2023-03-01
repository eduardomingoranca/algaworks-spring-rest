package com.algaworks.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class UsuarioUpdateInput {
    @NotBlank
    private String nome;

    @Email
    @NotBlank
    private String email;

}
