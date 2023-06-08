package com.algaworks.algafood.api.v1.openapi.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PagedModelOpenAPI<T> {
    private List<T> content;

    private Long size;

    private Long totalElements;

    private Long totalPages;

    private Long number;

}
