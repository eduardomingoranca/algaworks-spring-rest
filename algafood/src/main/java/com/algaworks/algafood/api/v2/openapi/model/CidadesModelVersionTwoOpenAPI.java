package com.algaworks.algafood.api.v2.openapi.model;

import com.algaworks.algafood.api.v2.model.CidadeModelVersionTwo;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
public class CidadesModelVersionTwoOpenAPI {
    private CidadeEmbeddedModelOpenAPI _embedded;
    private Links _links;

    @Data
    public static class CidadeEmbeddedModelOpenAPI {
        private List<CidadeModelVersionTwo> cidades;

    }
}
