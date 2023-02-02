package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

import static org.springframework.boot.WebApplicationType.NONE;

public class BuscaRestauranteMain {

    public static void main(String[] args) {
        // configurando a aplicacao para nao web
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(NONE)
                .run(args);

        RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);
        Optional<Restaurante> restaurante = restauranteRepository.findById(1L);

        if (restaurante.isPresent()) {
            System.out.println(restaurante.get().getNome());
            System.out.println(restaurante.get().getTaxaFrete());
        }

    }
}
