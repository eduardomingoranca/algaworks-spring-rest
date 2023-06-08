package com.algaworks.algafood.core.security.authorizationserver.config;

import com.algaworks.algafood.core.security.authorizationserver.config.token.PkceAuthorizationCodeTokenGranter;
import com.algaworks.algafood.core.security.authorizationserver.config.token.jwt.JwtCustomClaimsTokenEnhancer;
import com.algaworks.algafood.core.security.authorizationserver.config.token.jwt.JwtKeyStoreProperties;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

import static com.nimbusds.jose.JWSAlgorithm.RS256;
import static com.nimbusds.jose.jwk.KeyUse.SIGNATURE;
import static java.util.Arrays.asList;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtKeyStoreProperties keyStoreProperties;

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        // isAuthenticated() => para acessar o endpoint de check token precisa estar autenticado
//        security.checkTokenAccess("isAuthenticated()");
        security.checkTokenAccess("permitAll()")
                .tokenKeyAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        JwtCustomClaimsTokenEnhancer jwtCustomClaimsTokenEnhancer = new JwtCustomClaimsTokenEnhancer();
        enhancerChain.setTokenEnhancers(List.of(jwtCustomClaimsTokenEnhancer, jwtAccessTokenConverter()));

        JdbcAuthorizationCodeServices authorizationCodeServices = new JdbcAuthorizationCodeServices(dataSource);

        // atraves do authentication manager o authorization server
        // valida o usuario e senha do usuario final que eh passado
        // na autenticacao via API
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .authorizationCodeServices(authorizationCodeServices)
                // nao reutiza o refresh token
                .reuseRefreshTokens(false)
                .accessTokenConverter(jwtAccessTokenConverter())
                .tokenEnhancer(enhancerChain)
                .approvalStore(approvalStore(endpoints.getTokenStore()))
                .tokenGranter(tokenGranter(endpoints));
    }

    private ApprovalStore approvalStore(TokenStore tokenStore) {
        TokenApprovalStore approvalStore = new TokenApprovalStore();
        approvalStore.setTokenStore(tokenStore);

        return approvalStore;
    }

    @Bean
    public JWKSet jwkSet() {
        // passando a chave publica
        RSAKey.Builder builder = new RSAKey.Builder((RSAPublicKey) keyPair().getPublic())
                .keyUse(SIGNATURE)
                .algorithm(RS256)
                .keyID("algafood-key-id");

        return new JWKSet(builder.build());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();

        jwtAccessTokenConverter.setKeyPair(keyPair());

        return jwtAccessTokenConverter;
    }

    private KeyPair keyPair() {
        // senha para abrir o arquivo jks
        String keyStorePass = keyStoreProperties.getPassword();
        // nome do conjunto de chaves
        String keyPairAlias = keyStoreProperties.getKeypairAlias();

        // abrindo o arquivo jks
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(keyStoreProperties.getJksLocation(),
                keyStorePass.toCharArray());
        // obtendo o par de chaves
        return keyStoreKeyFactory.getKeyPair(keyPairAlias);
    }


    private TokenGranter tokenGranter(AuthorizationServerEndpointsConfigurer endpoints) {
        PkceAuthorizationCodeTokenGranter pkceAuthorizationCodeTokenGranter = new PkceAuthorizationCodeTokenGranter(
                endpoints.getTokenServices(), endpoints.getAuthorizationCodeServices(),
                endpoints.getClientDetailsService(), endpoints.getOAuth2RequestFactory());


        List<TokenGranter> granters = asList(pkceAuthorizationCodeTokenGranter, endpoints.getTokenGranter());

        return new CompositeTokenGranter(granters);
    }

}
