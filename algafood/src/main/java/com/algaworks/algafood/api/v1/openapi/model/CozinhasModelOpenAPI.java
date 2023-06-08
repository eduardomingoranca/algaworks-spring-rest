package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.CozinhaModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Getter
@Setter
public class CozinhasModelOpenAPI {
    private CozinhasEmbeddedModelOpenAPI _embedded;
    private Links _links;
    private PageModelOpenAPI page;

    @Data
    public static class CozinhasEmbeddedModelOpenAPI {
        private List<CozinhaModel> cozinhas;

    }

}
