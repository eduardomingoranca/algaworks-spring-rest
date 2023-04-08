package com.algaworks.algafood.client.algafood_java_client.client;

import com.algaworks.algafood.client.algafood_java_client.client.api.RestauranteClient;
import com.algaworks.algafood.client.algafood_java_client.client.api.exception.ClientAPIException;
import org.springframework.web.client.RestTemplate;

public class ListagemRestaurantesMain {

    public static void main(String[] args) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            RestauranteClient restauranteClient = new RestauranteClient(
                    restTemplate, "http://127.0.0.1:8080");

            // percorre cada restaurante da lista e imprime
            restauranteClient.listar().forEach(System.out::println);
        } catch (ClientAPIException e) {
            if (e.getProblem() != null) {
                System.out.println(e.getProblem());
                System.out.println(e.getProblem().getUserMessage());
            } else {
                System.out.println("Erro desconhecido!");
                e.printStackTrace();
            }
        }
    }

}
