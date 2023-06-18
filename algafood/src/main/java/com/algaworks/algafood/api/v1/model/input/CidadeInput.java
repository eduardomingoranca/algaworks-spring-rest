package com.algaworks.algafood.api.v1.model.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CidadeInput {
    @Schema(example = "Boston")
    @NotBlank
    private String nome;

    @Valid
    @NotNull
    private EstadoIDInput estado;

}
