package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.PermissaoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("PermissoesModel")
@Data
public class PermissaoModelOpenAPI {
    private PermissaoEmbeddedModelOpenAPI _embedded;
    private Links _links;

    @ApiModel("PermissoesEmbeddedModel")
    @Data
    public static class PermissaoEmbeddedModelOpenAPI {
        List<PermissaoModel> permissoes;

    }

}
