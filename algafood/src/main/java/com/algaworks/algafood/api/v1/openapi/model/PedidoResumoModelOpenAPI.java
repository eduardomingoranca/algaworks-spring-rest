package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("PedidosResumoModel")
@Getter
@Setter
public class PedidoResumoModelOpenAPI {
    private PedidoResumoEmbeddedModelOpenAPI _embedded;
    private Links _links;
    private PageModelOpenAPI page;

    @ApiModel("PedidoResumoEmbeddedModel")
    @Data
    private static class PedidoResumoEmbeddedModelOpenAPI {
        private List<PedidoResumoModel> pedidos;

    }

}
