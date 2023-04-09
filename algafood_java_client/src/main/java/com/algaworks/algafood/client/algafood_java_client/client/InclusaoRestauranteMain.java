package com.algaworks.algafood.client.algafood_java_client.client;

import com.algaworks.algafood.client.algafood_java_client.client.api.RestauranteClient;
import com.algaworks.algafood.client.algafood_java_client.client.api.exception.ClientAPIException;
import com.algaworks.algafood.client.algafood_java_client.client.model.input.id.CidadeIDInput;
import com.algaworks.algafood.client.algafood_java_client.client.model.input.id.CozinhaIDInput;
import com.algaworks.algafood.client.algafood_java_client.client.model.input.EnderecoInput;
import com.algaworks.algafood.client.algafood_java_client.client.model.input.RestauranteInput;
import com.algaworks.algafood.client.algafood_java_client.client.model.response.RestauranteModel;
import org.springframework.web.client.RestTemplate;

import static java.math.BigDecimal.valueOf;

public class InclusaoRestauranteMain {

    public static void main(String[] args) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            RestauranteClient restauranteClient = new RestauranteClient(
                    restTemplate, "http://127.0.0.1:8080");

            CozinhaIDInput cozinha = new CozinhaIDInput();
            cozinha.setId(1L);

            CidadeIDInput cidade = new CidadeIDInput();
            cidade.setId(3L);

            EnderecoInput endereco = new EnderecoInput();
            endereco.setCep("38400-999");
            endereco.setLogradouro("Rua Michael Lancaster");
            endereco.setNumero("2500");
            endereco.setComplemento("Perto do Mercado Morada do Sol");
            endereco.setBairro("Centro");
            endereco.setCidade(cidade);

            RestauranteInput restaurante = new RestauranteInput();
            restaurante.setNome("Thai World");
            restaurante.setTaxaFrete(valueOf(12));
            restaurante.setCozinha(cozinha);
            restaurante.setEndereco(endereco);

            RestauranteModel adicionar = restauranteClient.adicionar(restaurante);
            System.out.println(adicionar);
        } catch (ClientAPIException e) {
            if (e.getProblem() != null) {
                System.out.println(e.getProblem().getUserMessage());

                e.getProblem().getObjects().forEach(p ->
                        System.out.println("- " + p.getUserMessage()));
            } else {
                System.out.println("Erro desconhecido!");
                e.printStackTrace();
            }
        }
    }
}
