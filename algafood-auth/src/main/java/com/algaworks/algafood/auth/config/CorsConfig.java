package com.algaworks.algafood.auth.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static java.util.Collections.singletonList;

@Configuration
public class CorsConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.setAllowedOrigins(singletonList("*"));
        config.setAllowedMethods(singletonList("*"));
        config.setAllowedHeaders(singletonList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/oauth/token", config);

        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>();
        CorsFilter corsFilter = new CorsFilter(source);

        bean.setFilter(corsFilter);
        bean.setOrder(HIGHEST_PRECEDENCE);

        return bean;
    }
}
