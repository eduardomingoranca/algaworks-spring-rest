package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.EstadoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("EstadosModel")
@Data
public class EstadosModelOpenAPI {
    private EstadoEmbeddedModelOpenAPI _embedded;
    private Links _links;

    @ApiModel("EstadosEmbeddedModel")
    @Data
    public static class EstadoEmbeddedModelOpenAPI {
        private List<EstadoModel> estados;

    }

}
