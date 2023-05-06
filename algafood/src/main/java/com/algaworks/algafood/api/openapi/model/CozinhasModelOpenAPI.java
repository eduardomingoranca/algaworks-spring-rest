package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.CozinhaModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelOpenAPI {
    private CozinhasEmbeddedModelOpenAPI _embedded;
    private Links _links;
    private PageModelOpenAPI page;

    @ApiModel("CozinhasEmbeddedModel")
    @Data
    public static class CozinhasEmbeddedModelOpenAPI {
        private List<CozinhaModel> cozinhas;

    }

}
