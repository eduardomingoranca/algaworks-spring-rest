package com.algaworks.algafood.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

import static com.algaworks.algafood.core.web.AlgaMediaTypes.V2_APPLICATION_JSON;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // habilitando o cors globalmente
        registry.addMapping("/**") // permissao de rota/caminho
                .allowedMethods("*"); // permissao de metodos
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(V2_APPLICATION_JSON);
    }

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
