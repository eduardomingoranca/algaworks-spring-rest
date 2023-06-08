package com.algaworks.algafood.api.exceptionhandler.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

// so incluia na representacao json se o valor nao estiver nulo.
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    // status -> o codigo de status HTTP.
    private Integer status;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime timestamp;

    // type -> URI que especifica o tipo do problema.
    private String type;

    // title -> descricao do tipo do problema.
    private String title;

    // detail -> descricao especifica sobre a ocorrencia do erro.
    private String detail;

    private String userMessage;

    //  lista de propriedades violadas
    private List<Object> objects;

    @Getter
    @Builder
    public static class Object {
        private String name;

        private String userMessage;
    }

}
