package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
public class FormasPagamentoModelOpenAPI {
    private FormaPagamentoEmbeddedModelOpenAPI _embedded;
    private Links _links;

    @Data
    public static class FormaPagamentoEmbeddedModelOpenAPI {
        List<FormaPagamentoModel> formasPagamento;
    }

}
