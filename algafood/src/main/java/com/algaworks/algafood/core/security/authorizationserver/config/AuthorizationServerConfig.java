package com.algaworks.algafood.core.security.authorizationserver.config;

import com.algaworks.algafood.core.security.authorizationserver.config.jwt.JwtKeyStoreProperties;
import com.algaworks.algafood.core.security.authorizationserver.config.property.AlgafoodSecurityProperties;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.ProviderSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import static com.nimbusds.jose.jwk.RSAKey.load;
import static java.security.KeyStore.getInstance;
import static java.time.Duration.ofMinutes;
import static java.util.Arrays.asList;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration.applyDefaultSecurity;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.AUTHORIZATION_CODE;
import static org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS;
import static org.springframework.security.oauth2.core.ClientAuthenticationMethod.CLIENT_SECRET_BASIC;
import static org.springframework.security.oauth2.core.OAuth2TokenFormat.SELF_CONTAINED;
import static org.springframework.security.oauth2.server.authorization.client.RegisteredClient.withId;

@Configuration
public class AuthorizationServerConfig {

    // aplicando os filtros de securanca
    @Bean
    @Order(HIGHEST_PRECEDENCE)
    public SecurityFilterChain authFilterChain(HttpSecurity http) throws Exception {
        applyDefaultSecurity(http);
        return http
                .formLogin(withDefaults())
                .build();
    }

    // responsavel por escrever o authorization server que vai assinar os tokens
    @Bean
    public ProviderSettings providerSettings(AlgafoodSecurityProperties properties) {
        return ProviderSettings.builder()
                .issuer(properties.getProviderUrl())
                .build();
    }

    // responsavel por guardar os clients do authorization server
    @Bean
    public RegisteredClientRepository registeredClientRepository(PasswordEncoder passwordEncoder) {
        RegisteredClient algafoodBackend = withId("1")
                .clientId("algafood-backend")
                .clientSecret(passwordEncoder.encode("backend123"))
                .clientAuthenticationMethod(CLIENT_SECRET_BASIC)
                .authorizationGrantType(CLIENT_CREDENTIALS)
                .scope("READ")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(SELF_CONTAINED)
                        .accessTokenTimeToLive(ofMinutes(30))
                        .build())
                .build();

        RegisteredClient algafoodWeb = withId("2")
                .clientId("algafood-web")
                .clientSecret(passwordEncoder.encode("web123"))
                .clientAuthenticationMethod(CLIENT_SECRET_BASIC)
                .authorizationGrantType(AUTHORIZATION_CODE)
                .scope("READ")
                .scope("WRITE")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(SELF_CONTAINED)
                        .accessTokenTimeToLive(ofMinutes(15))
                        .build())
                .redirectUri("http://127.0.0.1:8080/authorized")
                .redirectUri("http://127.0.0.1:8080/swagger-ui/oauth2-redirect.html")
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(true)
                        .build())
                .build();

        RegisteredClient foodAnalytics = withId("3")
                .clientId("foodanalytics")
                .clientSecret(passwordEncoder.encode("web123"))
                .clientAuthenticationMethod(CLIENT_SECRET_BASIC)
                .authorizationGrantType(AUTHORIZATION_CODE)
                .scope("READ")
                .scope("WRITE")
                .tokenSettings(TokenSettings.builder()
                        .accessTokenFormat(SELF_CONTAINED)
                        .accessTokenTimeToLive(ofMinutes(30))
                        .build())
                .redirectUri("http://127.0.0.1:8082")
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(false)
                        .build())
                .build();

        return new InMemoryRegisteredClientRepository(asList(algafoodBackend, algafoodWeb, foodAnalytics));
    }

    @Bean
    public OAuth2AuthorizationService oAuth2AuthorizationService(JdbcOperations jdbcOperations,
                                                                 RegisteredClientRepository registeredClientRepository) {
        return new JdbcOAuth2AuthorizationService(jdbcOperations, registeredClientRepository);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(JwtKeyStoreProperties properties) throws IOException, KeyStoreException,
            CertificateException, NoSuchAlgorithmException, JOSEException {
        char[] keyStorePassword = properties.getPassword().toCharArray();
        String keypairAlias = properties.getKeypairAlias();

        Resource jksLocation = properties.getJksLocation();
        InputStream inputStream = jksLocation.getInputStream();
        KeyStore keyStore = getInstance("JKS");
        keyStore.load(inputStream, keyStorePassword);

        RSAKey rsaKey = load(keyStore, keypairAlias, keyStorePassword);

        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

}
