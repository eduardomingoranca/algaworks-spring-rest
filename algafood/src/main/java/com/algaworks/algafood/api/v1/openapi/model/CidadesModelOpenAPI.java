package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.CidadeModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
public class CidadesModelOpenAPI {
    private CidadeEmbeddedModelOpenAPI _embedded;
    private Links _links;

    @Data
    public static class CidadeEmbeddedModelOpenAPI {
        private List<CidadeModel> cidades;

    }

}
