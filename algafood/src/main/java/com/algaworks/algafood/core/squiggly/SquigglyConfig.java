package com.algaworks.algafood.core.squiggly;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bohnman.squiggly.web.RequestSquigglyContextProvider;
import com.github.bohnman.squiggly.web.SquigglyRequestFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

import static com.github.bohnman.squiggly.Squiggly.init;

@Configuration
public class SquigglyConfig {

    @Bean
    public FilterRegistrationBean<SquigglyRequestFilter> squigglyRequestFilter(ObjectMapper objectMapper) {
        // iniciando o squiggly
        init(objectMapper, new RequestSquigglyContextProvider("campos", null));

        // obtendo a url
        List<String> urlPatterns = Arrays.asList("/pedidos/*", "/restaurantes/*");

        // criando a instancia
        FilterRegistrationBean<SquigglyRequestFilter> filterRegistration = new FilterRegistrationBean<>();
        // atribuindo o filtro
        filterRegistration.setFilter(new SquigglyRequestFilter());
        // atribuindo a ordem do filtro
        filterRegistration.setOrder(1);
        // limitando nas urls informadas
        filterRegistration.setUrlPatterns(urlPatterns);

        return filterRegistration;
    }

}
