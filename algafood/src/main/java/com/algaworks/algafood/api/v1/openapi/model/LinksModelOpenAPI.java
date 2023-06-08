package com.algaworks.algafood.api.v1.openapi.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LinksModelOpenAPI {

    private LinkModel rel;

    @Setter
    @Getter
    private static class LinkModel {
        private String href;
        private boolean templated;

    }

}
