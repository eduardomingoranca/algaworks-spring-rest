package com.algaworks.algafood.api.v2.openapi.model;

import com.algaworks.algafood.api.v2.model.CozinhaModelVersionTwo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("CozinhasModel")
@Getter
@Setter
public class CozinhasModelVersionTwoOpenAPI {

    private CozinhasEmbeddedModelVersionTwoOpenAPI _embedded;
    private Links _links;
    private PageModelVersionTwoOpenAPI page;

    @ApiModel("CozinhasEmbeddedModel")
    @Data
    public static class CozinhasEmbeddedModelVersionTwoOpenAPI {
        private List<CozinhaModelVersionTwo> cozinhas;

    }

}
