package com.algaworks.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class EnderecoInput {

    @ApiModelProperty(example = "38400-000")
    @NotBlank
    private String cep;

    @ApiModelProperty(example = "Rua Floriano Peixoto")
    @NotBlank
    private String logradouro;

    @ApiModelProperty(example = "600")
    @NotBlank
    private String numero;

    @ApiModelProperty(example = "Apto 704")
    private String complemento;

    @ApiModelProperty(example = "Brasil")
    @NotBlank
    private String bairro;

    @Valid
    @NotNull
    private CidadeIDInput cidade;

}
