package com.algaworks.algafood.core.springdoc;

import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.OAUTH2;
import static java.util.Collections.singletonList;

@Configuration
@SecurityScheme(name = "security_auth",
        type = OAUTH2,
        flows = @OAuthFlows(authorizationCode = @OAuthFlow(
                authorizationUrl = "${springdoc.oAuthFlow.authorizationUrl}",
                tokenUrl = "${springdoc.oAuthFlow.tokenUrl}",
                scopes = {
                        @OAuthScope(name = "READ", description = "read scope"),
                        @OAuthScope(name = "WRITE", description = "write scope")
                }
        )))
public class SpringDocConfig {

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .group("Algafood API")
                .pathsToMatch("/v1/**")
                .addOpenApiCustomiser(openApi -> {
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

                    Tag cidade = new Tag()
                            .name("Cidades")
                            .description("Gerencia as cidades");

                    openApi.info(info)
                            .externalDocs(externalDocs)
                            .tags(singletonList(cidade));
                })
                .build();
    }

    @Bean
    public OpenApiCustomiser openApiCustomiser() {
        return openApi -> openApi.getPaths()
                .values()
                .forEach(pathItem -> pathItem.readOperationsMap()
                        .forEach(((httpMethod, operation) -> {
                                    ApiResponses responses = operation.getResponses();
                                    ApiResponse responseNotFound = new ApiResponse()
                                            .description("Recurso nao encontrado.");

                                    ApiResponse responseNotAcceptable = new ApiResponse()
                                            .description("Recurso nao possui representacao que poderia " +
                                                    "ser aceita pelo consumidor.");

                                    ApiResponse responseInternalServerError = new ApiResponse()
                                            .description("Erro interno no servidor.");

                                    ApiResponse responseBadRequest = new ApiResponse()
                                            .description("Requisicao invalida.");

                                    switch (httpMethod) {
                                        case GET -> {
                                            responses.addApiResponse("404", responseNotFound);
                                            responses.addApiResponse("406", responseNotAcceptable);
                                            responses.addApiResponse("500", responseInternalServerError);
                                        }
                                        case POST -> {
                                            responses.addApiResponse("400", responseBadRequest);
                                            responses.addApiResponse("500", responseInternalServerError);
                                        }
                                        case PUT -> {
                                            responses.addApiResponse("404", responseNotFound);
                                            responses.addApiResponse("400", responseBadRequest);
                                            responses.addApiResponse("500", responseInternalServerError);
                                        }
                                        case DELETE -> {
                                            responses.addApiResponse("404", responseNotFound);
                                            responses.addApiResponse("500", responseInternalServerError);
                                        }
                                        default -> responses.addApiResponse("500", responseInternalServerError);
                                    }
                                })
                        )
                );
    }

}
