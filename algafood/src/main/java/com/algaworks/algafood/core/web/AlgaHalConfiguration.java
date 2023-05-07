package com.algaworks.algafood.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;

import static com.algaworks.algafood.core.web.AlgaMediaTypes.V1_APPLICATION_JSON;
import static com.algaworks.algafood.core.web.AlgaMediaTypes.V2_APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
public class AlgaHalConfiguration {

    @Bean
    public HalConfiguration globalPolicy() {
        return new HalConfiguration()
                .withMediaType(APPLICATION_JSON)
                .withMediaType(V1_APPLICATION_JSON)
                .withMediaType(V2_APPLICATION_JSON);
    }

}
