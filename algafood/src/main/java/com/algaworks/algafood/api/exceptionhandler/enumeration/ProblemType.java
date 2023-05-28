package com.algaworks.algafood.api.exceptionhandler.enumeration;

import lombok.Getter;

@Getter
public enum ProblemType {
    MENSAGEM_INCOMPREENSIVEL("/mensagem-incompreensivel", "Mensagem incompreensivel"),
    RECURSO_NAO_ENCONTRADO("/recurso-nao-encontrado", "Recurso nao encontrado"),
    ERRO_NEGOCIO("/erro-negocio", "Violacao de regra de negocio"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parametro Invalido"),
    ERRO_DE_SISTEMA("/erro-de-sistema", "Erro de sistema"),
    DADOS_INVALIDOS("/dados-invalidos", "Dados invalidos"),
    ACESSO_NEGADO("/acesso-negado", "Acesso negado");

    private final String title;
    private final String uri;

    ProblemType(String path, String title) {
        this.uri = "https://algafood.com.br" + path;
        this.title = title;
    }

}
