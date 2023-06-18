package com.algaworks.algafood.core.web.config;

import com.algaworks.algafood.core.web.handler.APIRetirementHandler;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private APIRetirementHandler apiRetirementHandler;

    @Bean
    public Filter shallowEtagHeaderFilter() {
        // ao receber uma requisicao, quando retornar uma resposta
        // eh gerado um hash dessa resposta e coloca o cabecalho etag
        // alem disso eh verificado se o hash dessa resposta coincide
        // com a etag que esta no cabecalho da requisicao, caso seja
        // verdadeiro ele utiliza o que esta em cache.
        return new ShallowEtagHeaderFilter();
    }

}
