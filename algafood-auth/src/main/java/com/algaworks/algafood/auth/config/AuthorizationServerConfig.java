package com.algaworks.algafood.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.List;

import static java.util.Arrays.asList;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

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
                .withClient("foodanalytics")
                .secret(passwordEncoder.encode(""))
                .authorizedGrantTypes("authorization_code")
                .scopes("write", "read")
                .redirectUris("http://127.0.0.1:8082")

                .and()
                .withClient("webadmin")
                .authorizedGrantTypes("implicit")
                .scopes("write", "read")
                .redirectUris("http://aplicacao-cliente")

                .and()
                .withClient("faturamento")
                .secret(passwordEncoder.encode("faturamento123"))
                .authorizedGrantTypes("client_credentials")
                .scopes("write", "read")

                .and()
                .withClient("checktoken")
                .secret(passwordEncoder.encode("check123")); // 6 horas (padrao eh 12 horas)
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        // isAuthenticated() => para acessar o endpoint de check token precisa estar autenticado
//        security.checkTokenAccess("isAuthenticated()");
        security.checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // atraves do authentication manager o authorization server
        // valida o usuario e senha do usuario final que eh passado
        // na autenticacao via API
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                // nao reutiza o refresh token
                .reuseRefreshTokens(false)
                .tokenStore(redisTokenStore())
                .tokenGranter(tokenGranter(endpoints));
    }

    private TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        PkceAuthorizationCodeTokenGranter pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(
                endpoints.getTokenServices(), endpoints.getAuthorizationCodeServices(),
                endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory());


        List<TokenGranter> granters = asList(pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());

        return new CompositeTokenGranter(granters);
    }

}
