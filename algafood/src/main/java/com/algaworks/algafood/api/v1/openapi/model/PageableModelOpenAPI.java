package com.algaworks.algafood.api.v1.openapi.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PageableModelOpenAPI {
    private int page;

    private int size;

    private List<String> sort;

}
