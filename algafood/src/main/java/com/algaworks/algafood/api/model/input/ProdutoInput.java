package com.algaworks.algafood.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Setter
@Getter
public class ProdutoInput {

    @ApiModelProperty(example = "Frango agridoce", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "Delicioso frango agridoce com especiarias", required = true)
    @NotBlank
    private String descricao;

    @ApiModelProperty(example = "58.95", required = true)
    @NotNull
    @PositiveOrZero
    private BigDecimal preco;

    @ApiModelProperty(example = "false", required = true)
    @NotNull
    private Boolean ativo;

}
