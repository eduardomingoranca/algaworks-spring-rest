package com.algaworks.algafood.client.algafood_java_client.client.api;

import com.algaworks.algafood.client.algafood_java_client.client.api.exception.ClientAPIException;
import com.algaworks.algafood.client.algafood_java_client.client.model.response.RestauranteModel;
import com.algaworks.algafood.client.algafood_java_client.client.model.response.resumo.RestauranteResumoModel;
import com.algaworks.algafood.client.algafood_java_client.client.model.input.RestauranteInput;
import lombok.AllArgsConstructor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientResponseException;
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
        try {
            // obtendo a url junto com o recurso da requisicao
            URI resourceUri = create(url + RESOURCE_PATH);

            // requisicao GET
            RestauranteResumoModel[] restaurantes = restTemplate
                    .getForObject(resourceUri, RestauranteResumoModel[].class);

            assert restaurantes != null;
            return asList(restaurantes);
        } catch (RestClientResponseException e) {
            throw new ClientAPIException(e.getMessage(), e);
        }
    }

    public RestauranteModel adicionar(RestauranteInput restaurante) {
        URI resourceURI = create(url + RESOURCE_PATH);

        try {
            return restTemplate.postForObject(resourceURI, restaurante, RestauranteModel.class);
        } catch (HttpClientErrorException e) {
            throw new ClientAPIException(e.getMessage(), e);
        }
    }

}
