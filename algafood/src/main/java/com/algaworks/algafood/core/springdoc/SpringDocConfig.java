package com.algaworks.algafood.core.springdoc;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static io.swagger.v3.core.converter.ModelConverters.getInstance;
import static io.swagger.v3.oas.annotations.enums.SecuritySchemeType.OAUTH2;
import static java.util.Arrays.asList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
    private final static String BAD_REQUEST = "BadRequestResponse";
    private final static String NOT_FOUND = "NotFoundResponse";
    private final static String NOT_ACCEPTABLE = "NotAcceptableResponse";
    private final static String INTERNAL_SERVER_ERROR = "InternalServerErrorResponse";

    @Bean
    public OpenAPI openAPI() {
        License license = new License()
                .name("Apache 2.0")
                .url("http://springdoc.com");

        Info info = new Info()
                .title("AlgaFood API")
                .version("v1")
                .description("REST API do AlgaFood")
                .license(license);

        ExternalDocumentation externalDocs = new ExternalDocumentation()
                .description("AlgaWorks")
                .url("https://algaworks.com");

        Tag cidade = new Tag().name("Cidades")
                .description("Gerencia as cidades");

        Tag grupo = new Tag().name("Grupos")
                .description("Gerencia os grupos");

        Tag cozinha = new Tag().name("Cozinhas")
                .description("Gerencia as cozinhas");

        Tag formaPagamento = new Tag().name("Formas de pagamento")
                .description("Gerencia as formas de pagamento");

        Tag pedido = new Tag().name("Pedidos")
                .description("Gerencia os pedidos");

        Tag restaurante = new Tag().name("Restaurantes")
                .description("Gerencia os restaurantes");

        Components components = new Components()
                .schemas(gerarSchemas())
                .responses(gerarResponses());

        return new OpenAPI()
                .info(info)
                .externalDocs(externalDocs)
                .tags(asList(cidade, grupo, cozinha, formaPagamento,
                        pedido, restaurante))
                .components(components);
    }

    @Bean
    public OpenApiCustomiser openApiCustomiser() {
            return openApi -> {
            openApi.getPaths()
                    .values()
                    .forEach(pathItem -> pathItem.readOperationsMap()
                            .forEach((httpMethod, operation) -> {
                                ApiResponses responses = operation.getResponses();
                                ApiResponse apiResponseNotAcceptable = new ApiResponse()
                                        .$ref(NOT_ACCEPTABLE);
                                ApiResponse apiResponseInternalServerError = new ApiResponse()
                                        .$ref(INTERNAL_SERVER_ERROR);
                                ApiResponse apiResponseBadRequest = new ApiResponse()
                                        .$ref(BAD_REQUEST);

                                switch (httpMethod) {
                                    case GET -> {
                                        responses.addApiResponse("406", apiResponseNotAcceptable);
                                        responses.addApiResponse("500", apiResponseInternalServerError);
                                    }
                                    case POST, PUT -> {
                                        responses.addApiResponse("400", apiResponseBadRequest);
                                        responses.addApiResponse("500", apiResponseInternalServerError);
                                    }
                                    default ->
                                            responses.addApiResponse("500", apiResponseInternalServerError);
                                }
                            })
                    );
        };
    }

    private Map<String, Schema> gerarSchemas() {
        final Map<String, Schema> schemaMap = new HashMap<>();

        Map<String, Schema> problemSchema = getInstance().read(Problem.class);
        Map<String, Schema> problemObjectSchema = getInstance().read(Problem.Object.class);

        schemaMap.putAll(problemSchema);
        schemaMap.putAll(problemObjectSchema);

        return schemaMap;
    }

    private Map<String, ApiResponse> gerarResponses() {
        final Map<String, ApiResponse> apiResponseMap = new HashMap<>();

        Schema problema = new Schema<Problem>().$ref("Problema");
        MediaType problemaMediaType = new MediaType().schema(problema);

        Content content = new Content()
                .addMediaType(APPLICATION_JSON_VALUE,
                        problemaMediaType);

        ApiResponse badRequestResponse = new ApiResponse()
                .description("Requisicao invalida")
                .content(content);

        ApiResponse notFoundResponse = new ApiResponse()
                .description("Recurso nao encontrado")
                .content(content);

        ApiResponse notAcceptableResponse = new ApiResponse()
                .description("Recurso nao possui representacao que poderia ser aceita pelo consumidor")
                .content(content);

        ApiResponse internalServerErrorResponse = new ApiResponse()
                .description("Erro interno no servidor")
                .content(content);

        apiResponseMap.put(BAD_REQUEST, badRequestResponse);
        apiResponseMap.put(NOT_FOUND, notFoundResponse);
        apiResponseMap.put(NOT_ACCEPTABLE, notAcceptableResponse);
        apiResponseMap.put(INTERNAL_SERVER_ERROR, internalServerErrorResponse);

        return apiResponseMap;
    }

}
