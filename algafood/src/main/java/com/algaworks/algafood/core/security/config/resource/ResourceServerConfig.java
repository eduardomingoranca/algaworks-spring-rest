package com.algaworks.algafood.core.security.config.resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(POST, "/v1/cozinhas/**").hasAuthority("EDITAR_COZINHAS")
                .antMatchers(PUT, "/v1/cozinhas/**").hasAuthority("EDITAR_COZINHAS")
                .antMatchers(GET, "/v1/cozinhas/**").authenticated()
                .anyRequest().denyAll()
                .and()
                .cors().and()
                // habilitando/configurando um resource server
                .oauth2ResourceServer()
                .jwt()
                .jwtAuthenticationConverter(jwtAuthenticationConverter());
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        // o token recebido na requisicao (Resource Server) tem que extrair as authorities e
        // converter em uma granted authority
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
            List<String> authorities = jwt.getClaimAsStringList("authorities");

            if (authorities == null)
                authorities = emptyList();

            return authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(toList());
        });

        return jwtAuthenticationConverter;
    }

}
