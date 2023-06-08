package com.algaworks.algafood.core.springdoc;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("Algafood API Admin")
                .pathsToMatch("/v1/**")
                .addOpenApiCustomiser(openApi -> {
                    License license = new License()
                            .name("Apache 2.0")
                            .url("http://springdoc.com");

                    Info info = new Info()
                            .title("Algafood API Admin")
                            .version("v1")
                            .description("REST API do Algafood")
                            .license(license);

                    ExternalDocumentation externalDocs = new ExternalDocumentation()
                            .description("AlgaWorks")
                            .url("https://algaworks.com");

                    openApi.info(info).externalDocs(externalDocs);
                })
                .build();
    }

    @Bean
    public GroupedOpenApi groupedOpenApiCliente() {
        return GroupedOpenApi.builder()
                .group("Algafood API Cliente")
                .pathsToMatch("/cliente/v1/**")
                .addOpenApiCustomiser(openApi -> {
                    License license = new License()
                            .name("Apache 2.0")
                            .url("http://springdoc.com");

                    Info info = new Info()
                            .title("Algafood API Cliente")
                            .version("v1")
                            .description("REST API do Algafood")
                            .license(license);

                    ExternalDocumentation externalDocs = new ExternalDocumentation()
                            .description("AlgaWorks")
                            .url("https://algaworks.com");

                    openApi.info(info).externalDocs(externalDocs);
                })
                .build();
    }

}
