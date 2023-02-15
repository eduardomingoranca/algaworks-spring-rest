package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensivel"),
    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade nao encontrada"),
    EXCEPTION_DE_NEGOCIO("/exception-de-negocio", "Exception de negocio"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parametro Invalido");

    private final String title;
    private final String uri;

    ProblemType(String path, String title) {
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }

}
