package com.algaworks.algafood.api.v2.openapi.model;

import com.algaworks.algafood.api.v2.model.CozinhaModelVersionTwo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Getter
@Setter
public class CozinhasModelVersionTwoOpenAPI {

    private CozinhasEmbeddedModelVersionTwoOpenAPI _embedded;
    private Links _links;
    private PageModelVersionTwoOpenAPI page;

    @Data
    public static class CozinhasEmbeddedModelVersionTwoOpenAPI {
        private List<CozinhaModelVersionTwo> cozinhas;

    }

}
