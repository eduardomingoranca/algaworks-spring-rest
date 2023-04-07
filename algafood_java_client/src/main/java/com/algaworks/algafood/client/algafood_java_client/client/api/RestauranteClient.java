package com.algaworks.algafood.client.algafood_java_client.client.api;

import com.algaworks.algafood.client.algafood_java_client.client.model.RestauranteResumoModel;
import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static java.net.URI.create;
import static java.util.Arrays.asList;

@AllArgsConstructor
public class RestauranteClient {
    private static final String RESOURCE_PATH = "/restaurantes";

    // classe responsavel por realizar as requisicoes HTTP
    private RestTemplate restTemplate;

    private String url;

    public List<RestauranteResumoModel> listar() {
        // obtendo a url junto com o recurso da requisicao
        URI resourceUri = create(url + RESOURCE_PATH);

        // requisicao GET
        RestauranteResumoModel[] restaurantes = restTemplate
                .getForObject(resourceUri, RestauranteResumoModel[].class);

        assert restaurantes != null;
        return asList(restaurantes);
    }

}
