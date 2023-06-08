package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.PermissaoModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
public class PermissaoModelOpenAPI {
    private PermissaoEmbeddedModelOpenAPI _embedded;
    private Links _links;

    @Data
    public static class PermissaoEmbeddedModelOpenAPI {
        List<PermissaoModel> permissoes;

    }

}
