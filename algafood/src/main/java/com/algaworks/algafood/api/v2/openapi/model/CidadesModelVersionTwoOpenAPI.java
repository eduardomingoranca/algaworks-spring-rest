package com.algaworks.algafood.api.v2.openapi.model;

import com.algaworks.algafood.api.v2.model.CidadeModelVersionTwo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CidadesModel")
@Data
public class CidadesModelVersionTwoOpenAPI {
    private CidadeEmbeddedModelOpenAPI _embedded;
    private Links _links;

    @ApiModel("CidadesEmbeddedModel")
    @Data
    public static class CidadeEmbeddedModelOpenAPI {
        private List<CidadeModelVersionTwo> cidades;

    }
}
