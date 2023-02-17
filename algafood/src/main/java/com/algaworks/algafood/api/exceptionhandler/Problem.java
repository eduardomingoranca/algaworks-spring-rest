package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

// so incluia na representacao json se o valor nao estiver nulo.
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class Problem {

    // status -> o codigo de status HTTP.
    private Integer status;

    // type -> URI que especifica o tipo do problema.
    private String type;

    // title -> descricao do tipo do problema.
    private String title;

    // detail -> descricao especifica sobre a ocorrencia do erro.
    private String detail;


    private String userMessage;
}
