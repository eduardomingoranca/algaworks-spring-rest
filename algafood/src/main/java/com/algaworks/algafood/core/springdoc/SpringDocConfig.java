package com.algaworks.algafood.core.springdoc;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI() {
        License license = new License()
                .name("Apache 2.0")
                .url("http://springdoc.com");

        Info info = new Info()
                .title("Algafood API")
                .version("v1")
                .description("REST API do Algafood")
                .license(license);

        ExternalDocumentation externalDocs = new ExternalDocumentation()
                .description("AlgaWorks")
                .url("https://algaworks.com");

        return new OpenAPI().info(info).externalDocs(externalDocs);
    }

}
