package com.algaworks.algafood.core.openapi;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RepresentationBuilder;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.service.Tag;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;
import java.util.function.Consumer;

import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static springfox.documentation.builders.PathSelectors.any;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.spi.DocumentationType.OAS_30;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
@RequiredArgsConstructor
public class SpringFoxConfig {
    public static final String REQUISICAO_INVALIDA = "Requisicao invalida (erro de cliente).";
    public static final String ERRO_INTERNO_DO_SERVIDOR = "Erro interno do servidor.";
    public static final String REPRESENTACAO_INVALIDA = "Recurso nao possui representacao que poderia ser aceita pelo consumidor.";
    public static final String FORMATO_NAO_SUPORTADO = "Requisicao recusada porque o corpo esta em um formato nao suportado.";

    private final TypeResolver typeResolver;

    @Bean
    public Docket apiDocket() {
        // Docket => classe do springfox que representa
        // a configuracao da api para gerar a definicao
        // usando a especificacao openapi
        // eh um sumario para configurar um conjunto de
        // servicos que devem ser documentados.
        Tag firstTag = new Tag("Cidades", "Gerencia as cidades");
        Tag secondTag = new Tag("Grupos", "Gerencia os grupos de usuarios");

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
                .globalResponses(DELETE, globalDeleteResponseMessages())
                // adicionando um model
                .additionalModels(typeResolver.resolve(Problem.class))
                .apiInfo(apiInfo())
                .tags(firstTag, secondTag);
    }

    @Bean
    public JacksonModuleRegistrar springFoxJacksonConfig() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        return objectMapper -> objectMapper.registerModule(javaTimeModule);
    }

    // codigo de status padrao para o metodo DELETE
    private List<Response> globalDeleteResponseMessages() {
        Response badRequest = new ResponseBuilder()
                .code(valueOf(BAD_REQUEST.value()))
                .description(REQUISICAO_INVALIDA)
                .representation(APPLICATION_JSON)
                .apply(getProblemaModelReference())
                .build();

        Response internalServerError = new ResponseBuilder()
                .code(valueOf(INTERNAL_SERVER_ERROR.value()))
                .description(ERRO_INTERNO_DO_SERVIDOR)
                .representation(APPLICATION_JSON)
                .apply(getProblemaModelReference())
                .build();

        return asList(badRequest, internalServerError);
    }


    // codigo de status padrao para o metodo PUT
    private List<Response> globalPutResponseMessages() {
        Response badRequest = new ResponseBuilder()
                .code(valueOf(BAD_REQUEST.value()))
                .description(REQUISICAO_INVALIDA)
                .representation(APPLICATION_JSON)
                .apply(getProblemaModelReference())
                .build();

        Response internalServerError = new ResponseBuilder()
                .code(valueOf(INTERNAL_SERVER_ERROR.value()))
                .description(ERRO_INTERNO_DO_SERVIDOR)
                .representation(APPLICATION_JSON)
                .apply(getProblemaModelReference())
                .build();

        Response notAcceptable = new ResponseBuilder()
                .code(valueOf(NOT_ACCEPTABLE.value()))
                .description(REPRESENTACAO_INVALIDA)
                .build();

        Response unsupportedMediaType = new ResponseBuilder()
                .code(valueOf(UNSUPPORTED_MEDIA_TYPE.value()))
                .description(FORMATO_NAO_SUPORTADO)
                .representation(APPLICATION_JSON)
                .apply(getProblemaModelReference())
                .build();

        return asList(badRequest, internalServerError, notAcceptable, unsupportedMediaType);
    }

    // codigo de status padrao para o metodo POST
    private List<Response> globalPostResponseMessages() {
        Response badRequest = new ResponseBuilder()
                .code(valueOf(BAD_REQUEST.value()))
                .description(REQUISICAO_INVALIDA)
                .representation(APPLICATION_JSON)
                .apply(getProblemaModelReference())
                .build();

        Response internalServerError = new ResponseBuilder()
                .code(valueOf(INTERNAL_SERVER_ERROR.value()))
                .description(ERRO_INTERNO_DO_SERVIDOR)
                .representation(APPLICATION_JSON)
                .apply(getProblemaModelReference())
                .build();

        Response notAcceptable = new ResponseBuilder()
                .code(valueOf(NOT_ACCEPTABLE.value()))
                .description(REPRESENTACAO_INVALIDA)
                .build();

        Response unsupportedMediaType = new ResponseBuilder()
                .code(valueOf(UNSUPPORTED_MEDIA_TYPE.value()))
                .description(FORMATO_NAO_SUPORTADO)
                .representation(APPLICATION_JSON)
                .apply(getProblemaModelReference())
                .build();

        return asList(badRequest, internalServerError, notAcceptable, unsupportedMediaType);
    }

    // codigo de status padrao para o metodo GET
    private List<Response> globalGetResponseMessages() {
        Response internalServerError = new ResponseBuilder()
                .code(valueOf(INTERNAL_SERVER_ERROR.value()))
                .description(ERRO_INTERNO_DO_SERVIDOR)
                .representation(APPLICATION_JSON)
                .apply(getProblemaModelReference())
                .build();

        Response notAcceptable = new ResponseBuilder()
                .code(valueOf(NOT_ACCEPTABLE.value()))
                .description(REPRESENTACAO_INVALIDA)
                .build();

        return asList(internalServerError, notAcceptable);
    }

    private Consumer<RepresentationBuilder> getProblemaModelReference() {
        return r -> r.model(m -> m.name("Problema").referenceModel(ref ->
                ref.key(k -> k.qualifiedModelName(
                        q -> q.name("Problema").namespace("com.algaworks.algafood.api.exceptionhandler.model")))));
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
