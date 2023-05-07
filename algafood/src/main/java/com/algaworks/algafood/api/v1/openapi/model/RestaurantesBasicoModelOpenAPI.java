package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("RestaurantesBasicoModel")
@Getter
@Setter
public class RestaurantesBasicoModelOpenAPI {
    private RestaurantesBasicoEmbeddedModelOpenAPI _embedded;
    private Links _links;

    @ApiModel("RestaurantesBasicoEmbeddedModel")
    @Data
    public static class RestaurantesBasicoEmbeddedModelOpenAPI {
        private List<RestauranteBasicoModel> restaurantes;
    }

}
