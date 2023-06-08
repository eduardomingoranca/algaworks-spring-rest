package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.ProdutoModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Getter
@Setter
public class ProdutoModelOpenAPI {
    private ProdutoEmbeddedModelOpenAPI _embedded;
    private Links _links;

    @Data
    public static class ProdutoEmbeddedModelOpenAPI {
        private List<ProdutoModel> produtos;

    }

}
