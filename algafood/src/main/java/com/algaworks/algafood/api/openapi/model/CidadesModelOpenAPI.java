package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.CidadeModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CidadesModel")
@Data
public class CidadesModelOpenAPI {
    private CidadeEmbeddedModelOpenAPI _embedded;
    private Links _links;

    @ApiModel("CidadesEmbeddedModel")
    @Data
    public static class CidadeEmbeddedModelOpenAPI {
        private List<CidadeModel> cidades;

    }

}
