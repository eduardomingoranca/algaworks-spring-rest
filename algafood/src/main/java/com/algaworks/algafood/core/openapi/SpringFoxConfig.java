package com.algaworks.algafood.core.openapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;

import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;
import static springfox.documentation.builders.PathSelectors.any;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.OAS_30;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {

    @Bean
    public Docket apiDocket() {
        // Docket => classe do springfox que representa
        // a configuracao da api para gerar a definicao
        // usando a especificacao openapi
        // eh um sumario para configurar um conjunto de
        // servicos que devem ser documentados.
        Tag firstTag = new Tag("Cidades", "Gerencia as cidades");

        return new Docket(OAS_30)
                .select()
                .apis(basePackage("com.algaworks.algafood.api"))
                .paths(any())
//                .paths(ant("/restaurantes/*"))
                .build()
                .useDefaultResponseMessages(false) // deixa apenas o status de sucesso
                .globalResponses(GET, globalGetResponseMessages())
                .globalResponses(POST, globalPostResponseMessages())
                .globalResponses(PUT, globalPutResponseMessages())
                .apiInfo(apiInfo())
                .tags(firstTag);
    }

    // codigo de status padrao para o metodo PUT
    private List<Response> globalPutResponseMessages() {
        Response badRequest = new ResponseBuilder()
                .code(valueOf(BAD_REQUEST.value()))
                .description("Erro na requisicao realizada.")
                .build();

        Response internalServerError = new ResponseBuilder()
                .code(valueOf(INTERNAL_SERVER_ERROR.value()))
                .description("Erro interno do Servidor.")
                .build();

        Response notAcceptable = new ResponseBuilder()
                .code(valueOf(NOT_ACCEPTABLE.value()))
                .description("Recurso nao aceito.")
                .build();

        Response unsupportedMediaType = new ResponseBuilder()
                .code(valueOf(UNSUPPORTED_MEDIA_TYPE.value()))
                .description("Formato de midia dos dados nao suportado.")
                .build();

        return asList(badRequest, internalServerError, notAcceptable, unsupportedMediaType);
    }

    // codigo de status padrao para o metodo POST
    private List<Response> globalPostResponseMessages() {
        Response badRequest = new ResponseBuilder()
                .code(valueOf(BAD_REQUEST.value()))
                .description("Erro na requisicao realizada.")
                .build();

        Response internalServerError = new ResponseBuilder()
                .code(valueOf(INTERNAL_SERVER_ERROR.value()))
                .description("Erro interno do Servidor.")
                .build();

        Response notAcceptable = new ResponseBuilder()
                .code(valueOf(NOT_ACCEPTABLE.value()))
                .description("Recurso nao aceito.")
                .build();

        Response unsupportedMediaType = new ResponseBuilder()
                .code(valueOf(UNSUPPORTED_MEDIA_TYPE.value()))
                .description("Formato de midia dos dados nao suportado.")
                .build();

        return asList(badRequest, internalServerError, notAcceptable, unsupportedMediaType);
    }

    // codigo de status padrao para o metodo GET
    private List<Response> globalGetResponseMessages() {
        return asList(
                new ResponseBuilder()
                        .code(valueOf(INTERNAL_SERVER_ERROR.value()))
                        .description("Erro interno do Servidor")
                        .build(),
                new ResponseBuilder()
                        .code(valueOf(NOT_ACCEPTABLE.value()))
                        .description("Recurso nao possui representacao que pode ser aceita pelo consumidor")
                        .build()
        );
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("Algaworks", "https://www.algaworks.com",
                "contato@algaworks.com");

        return new ApiInfoBuilder()
                .title("AlgaFood API")
                .description("API aberta para clientes e restaurantes")
                .version("1")
                .contact(contact)
                .build();
    }

}
