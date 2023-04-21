package com.algaworks.algafood.api.exceptionhandler.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@ApiModel("Problema")
// so incluia na representacao json se o valor nao estiver nulo.
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    @ApiModelProperty(example = "400", position = 1)
    // status -> o codigo de status HTTP.
    private Integer status;

    @ApiModelProperty(example = "2023-01-01T18:09:02.70844Z", position = 5)
    private OffsetDateTime timestamp;

    @ApiModelProperty(example = "https://algafood.com.br/dados-invalidos", position = 10)
    // type -> URI que especifica o tipo do problema.
    private String type;

    @ApiModelProperty(example = "Dados invalidos.", position = 15)
    // title -> descricao do tipo do problema.
    private String title;

    @ApiModelProperty(example = "Um ou mais campos estao invalidos. Faca o preenchimento correto e tente novamente.",
            position = 20)
    // detail -> descricao especifica sobre a ocorrencia do erro.
    private String detail;

    @ApiModelProperty(example = "Um ou mais campos estao invalidos. Faca o preenchimento correto e tente novamente.",
            position = 25)
    private String userMessage;

    @ApiModelProperty(value = "Lista de objetos ou campos que geraram o erro (opcional).", position = 30)
    //  lista de propriedades violadas
    private List<Object> objects;

    @ApiModel("ObjetoProblema")
    @Getter
    @Builder
    public static class Object {
        @ApiModelProperty(example = "preco")
        private String name;

        @ApiModelProperty(example = "O preco eh obrigatorio.")
        private String userMessage;
    }

}
