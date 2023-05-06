package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.model.ProdutoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("ProdutosModel")
@Getter
@Setter
public class ProdutoModelOpenAPI {
    private ProdutoEmbeddedModelOpenAPI _embedded;
    private Links _links;

    @ApiModel("ProdutosEmbeddedModel")
    @Data
    public static class ProdutoEmbeddedModelOpenAPI {
        private List<ProdutoModel> produtos;

    }

}
