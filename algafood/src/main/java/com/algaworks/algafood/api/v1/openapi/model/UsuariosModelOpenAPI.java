package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.UsuarioModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("UsuariosModel")
@Getter
@Setter
public class UsuariosModelOpenAPI {
    private UsuarioEmbeddedModelOpenAPI _embedded;
    private Links _links;

    @ApiModel("UsuariosEmbeddedModel")
    @Data
    public static class UsuarioEmbeddedModelOpenAPI {
        private List<UsuarioModel> usuarios;
    }

}
