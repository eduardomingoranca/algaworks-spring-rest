package com.algaworks.algafood.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

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
                .authorizedGrantTypes("password", "refresh_token")
                // escopos do cliente
                .scopes("write", "read")
                // tempo de validade do token de acesso
                .accessTokenValiditySeconds(6 * 60 * 60) // 6 horas
                // tempo de validade do refresh token
                .refreshTokenValiditySeconds(60 * 24 * 60 * 60) // 60 dias
                .and()
                .withClient("checktoken")
                .secret(passwordEncoder.encode("check123")); // 6 horas (padrao eh 12 horas)
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        // isAuthenticated() => para acessar o endpoint de check token precisa estar autenticado
//        security.checkTokenAccess("isAuthenticated()");
        security.checkTokenAccess("permitAll()");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // atraves do authentication manager o authorization server
        // valida o usuario e senha do usuario final que eh passado
        // na autenticacao via API
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                // nao reutiza o refresh token
                .reuseRefreshTokens(false);
    }

}
