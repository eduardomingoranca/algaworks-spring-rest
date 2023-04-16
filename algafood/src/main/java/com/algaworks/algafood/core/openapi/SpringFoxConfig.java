package com.algaworks.algafood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.RequestHandlerSelectors.any;
import static springfox.documentation.spi.DocumentationType.OAS_30;

@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket apiDocket() {
        // Docket => classe do springfox que representa
        // a configuracao da api para gerar a definicao
        // usando a especificacao openapi
        // eh um sumario para configurar um conjunto de
        // servicos que devem ser documentados.
        return new Docket(OAS_30)
                .select()
                .apis(any())
                .build();
    }

}
