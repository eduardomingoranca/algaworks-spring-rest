package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.EstadoModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
public class EstadosModelOpenAPI {
    private EstadoEmbeddedModelOpenAPI _embedded;
    private Links _links;

    @Data
    public static class EstadoEmbeddedModelOpenAPI {
        private List<EstadoModel> estados;

    }

}
