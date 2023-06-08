package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.GrupoModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
public class GruposModelOpenAPI {
    private GrupoEmbeddedModelOpenAPI _embedded;
    private Links _links;

    @Data
    public static class GrupoEmbeddedModelOpenAPI {
        List<GrupoModel> grupos;

    }

}
