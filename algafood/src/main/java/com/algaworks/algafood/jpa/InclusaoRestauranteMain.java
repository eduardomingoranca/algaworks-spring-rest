package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import static java.math.BigDecimal.valueOf;
import static org.springframework.boot.WebApplicationType.NONE;

public class InclusaoRestauranteMain {

    public static void main(String[] args) {
        // configurando a aplicacao para nao web
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(NONE)
                .run(args);

        RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);

        Restaurante restaurante = new Restaurante();
        restaurante.setNome("Le President");
        restaurante.setTaxaFrete(valueOf(16.5));

        Restaurante restaurante2 = new Restaurante();
        restaurante2.setNome("Le Velmont");
        restaurante2.setTaxaFrete(valueOf(20.5));

        restaurante = restauranteRepository.save(restaurante);
        restaurante2 = restauranteRepository.save(restaurante2);

        System.out.println(restaurante.getId() + " " + restaurante.getNome() + " " + restaurante.getTaxaFrete());
        System.out.println(restaurante2.getId() + " " + restaurante2.getNome() + " " + restaurante2.getTaxaFrete());
    }
}
