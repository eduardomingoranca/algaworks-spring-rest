package com.algaworks.algafood.api.exceptionhandler.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

// so incluia na representacao json se o valor nao estiver nulo.
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@Schema(name = "Problema")
public class Problem {

    @Schema(example = "400")
    // status -> o codigo de status HTTP.
    private Integer status;

    @Schema(example = "http://127.0.0.1:8080/dados-invalidos")
    // type -> URI que especifica o tipo do problema.
    private String type;

    @Schema(example = "Dados invalidos")
    // title -> descricao do tipo do problema.
    private String title;

    @Schema(example = "Um ou mais campos estao invalidos. Faca o preenchimento correto e tente novamente.")
    // detail -> descricao especifica sobre a ocorrencia do erro.
    private String detail;

    @Schema(example = "Um ou mais campos estao invalidos. Faca o preenchimento correto e tente novamente.")
    private String userMessage;

    @Schema(example = "2023-06-10T11:21:50.902245498Z")
    private OffsetDateTime timestamp;

    @Schema(description = "Lista de objetos ou campos que geraram o erro.")
    //  lista de propriedades violadas
    private List<Object> objects;

    @Getter
    @Builder
    @Schema(name = "ObjetoProblema")
    public static class Object {
        @Schema(example = "preco")
        private String name;

        @Schema(example = "O preco eh invalido")
        private String userMessage;
    }

}
