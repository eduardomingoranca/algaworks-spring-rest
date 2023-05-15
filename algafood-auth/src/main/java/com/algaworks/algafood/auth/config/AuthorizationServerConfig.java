package com.algaworks.algafood.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // detalhes dos clientes do authorization server
        // o cliente eh a aplicacao que vai acessar os recursos do
        // resource server usando o access token que eh emitido pelo
        // authorization server.
        clients.inMemory()
                // autenticando o cliente
                .withClient("algafood-web")
                .secret(passwordEncoder.encode("web123"))
                // identificando o fluxo
                .authorizedGrantTypes("password")
                // escopos do cliente
                .scopes("write", "read")
                // tempo de validade do token de acesso
                .accessTokenValiditySeconds(60 * 60 * 6);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // atraves do authentication manager o authorization server
        // valida o usuario e senha do usuario final que eh passado
        // na autenticacao via API
        endpoints.authenticationManager(authenticationManager);
    }
}
