package com.algaworks.algafood.api.openapi.model;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@ApiModel("Links")
public class LinksModelOpenAPI {

    private LinkModel rel;

    @Setter
    @Getter
    @ApiModel("Link")
    private static class LinkModel {
        private String href;
        private boolean templated;

    }

}
