package com.algaworks.algafood.client.algafood_java_client.client;

import com.algaworks.algafood.client.algafood_java_client.client.api.RestauranteClient;
import org.springframework.web.client.RestTemplate;

public class ListagemRestaurantesMain {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        RestauranteClient restauranteClient = new RestauranteClient(
                restTemplate, "http://127.0.0.1:8080");

        // percorre cada restaurante da lista e imprime
        restauranteClient.listar().forEach(System.out::println);
    }

}
