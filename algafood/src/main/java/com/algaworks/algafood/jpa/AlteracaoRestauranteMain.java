package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import static java.math.BigDecimal.valueOf;
import static org.springframework.boot.WebApplicationType.NONE;

public class AlteracaoRestauranteMain {

    public static void main(String[] args) {
        // configurando a aplicacao para nao web
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(NONE)
                .run(args);

        RestauranteRepository restauranteRepository = applicationContext.getBean(RestauranteRepository.class);

        Restaurante restaurante = new Restaurante();
        restaurante.setId(3L);
        restaurante.setNome("Tuk Tuk Comida Indiana");
        restaurante.setTaxaFrete(valueOf(11.5));

        restauranteRepository.adicionar(restaurante);
    }
}
