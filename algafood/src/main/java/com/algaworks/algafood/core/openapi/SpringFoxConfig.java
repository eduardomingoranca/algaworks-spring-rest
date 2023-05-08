package com.algaworks.algafood.core.openapi;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.v1.model.*;
import com.algaworks.algafood.api.v1.openapi.model.*;
import com.algaworks.algafood.api.v2.model.CidadeModelVersionTwo;
import com.algaworks.algafood.api.v2.model.CozinhaModelVersionTwo;
import com.algaworks.algafood.api.v2.openapi.model.CidadesModelVersionTwoOpenAPI;
import com.algaworks.algafood.api.v2.openapi.model.CozinhasModelVersionTwoOpenAPI;
import com.fasterxml.classmate.TypeResolver;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.context.request.ServletWebRequest;
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

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.List;
import java.util.function.Consumer;

import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static springfox.documentation.builders.PathSelectors.ant;
import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;
import static springfox.documentation.schema.AlternateTypeRules.newRule;
import static springfox.documentation.spi.DocumentationType.OAS_30;

@Configuration
@Import(BeanValidatorPluginsConfiguration.class)
@RequiredArgsConstructor
public class SpringFoxConfig {
    public static final String REQUISICAO_INVALIDA = "Requisicao invalida (erro de cliente).";
    public static final String ERRO_INTERNO_DO_SERVIDOR = "Erro interno do servidor.";
    public static final String REPRESENTACAO_INVALIDA = "Recurso nao possui representacao que poderia ser aceita pelo consumidor.";
    public static final String FORMATO_NAO_SUPORTADO = "Requisicao recusada porque o corpo esta em um formato nao suportado.";


    @Bean
    public Docket apiDocket() {
        // Docket => classe do springfox que representa
        // a configuracao da api para gerar a definicao
        // usando a especificacao openapi
        // eh um sumario para configurar um conjunto de
        // servicos que devem ser documentados.
        Tag firstTag = new Tag("Cidades", "Gerencia as cidades");
        Tag secondTag = new Tag("Grupos", "Gerencia os grupos de usuarios");
        Tag thirdTag = new Tag("Cozinhas", "Gerencia as cozinhas");
        Tag fourthTag = new Tag("Formas de Pagamento", "Gerencia as formas de pagamento");
        Tag fifthTag = new Tag("Pedidos", "Gerencia os pedidos");
        Tag sixthTag = new Tag("Restaurantes", "Gerencia os restaurantes");
        Tag seventhTag = new Tag("Estados", "Gerencia os estados");
        Tag eighthTag = new Tag("Produtos", "Gerencia os produtos");
        Tag ninethTag = new Tag("Usuarios", "Gerencia os usuarios");
        Tag tenthTag = new Tag("Estatisticas", "Estatisticas da AlgaFood");
        TypeResolver typeResolver = new TypeResolver();

        return new Docket(OAS_30)
                .groupName("V1")
                .select()
                .apis(basePackage("com.algaworks.algafood.api"))
                .paths(ant("/v1/**"))
                .build()
                .useDefaultResponseMessages(false) // deixa apenas o status de sucesso
                .globalResponses(GET, globalGetResponseMessages())
                .globalResponses(POST, globalPostResponseMessages())
                .globalResponses(PUT, globalPutResponseMessages())
                .globalResponses(DELETE, globalDeleteResponseMessages())
                // adicionando um model
                .additionalModels(typeResolver.resolve(Problem.class))
                // ignorando parametros de um tipo especifico
                .ignoredParameterTypes(ServletWebRequest.class, URL.class, URI.class, URLStreamHandler.class,
                        Resource.class, File.class, InputStream.class)
                // substituindo o model
                .directModelSubstitute(Pageable.class, PageableModelOpenAPI.class)
                .directModelSubstitute(Links.class, LinksModelOpenAPI.class)
                .alternateTypeRules(newRule(typeResolver.resolve(PagedModel.class, CozinhaModel.class),
                        CozinhasModelOpenAPI.class))
                .alternateTypeRules(newRule(typeResolver.resolve(PagedModel.class, PedidoResumoModel.class),
                        PedidoResumoModelOpenAPI.class))
                .alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, CidadeModel.class),
                        CidadesModelOpenAPI.class))
                .alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, EstadoModel.class),
                        EstadosModelOpenAPI.class))
                .alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, FormaPagamentoModel.class),
                        FormasPagamentoModelOpenAPI.class))
                .alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, GrupoModel.class),
                        GruposModelOpenAPI.class))
                .alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, PermissaoModel.class),
                        PermissaoModelOpenAPI.class))
                .alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, ProdutoModel.class),
                        ProdutoModelOpenAPI.class))
                .alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, RestauranteBasicoModel.class),
                        RestaurantesBasicoModelOpenAPI.class))
                .alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, UsuarioModel.class),
                        UsuariosModelOpenAPI.class))
                .apiInfo(apiInfo())
                .tags(firstTag, secondTag, thirdTag, fourthTag, fifthTag, sixthTag, seventhTag, eighthTag,
                        ninethTag, tenthTag);
    }

    @Bean
    public Docket apiDocketVersionTwo() {
        Tag firstTag = new Tag("Cidades", "Gerencia as cidades");
        Tag secondTag = new Tag("Cozinhas", "Gerencia as cozinhas");
        TypeResolver typeResolver = new TypeResolver();

        return new Docket(OAS_30)
                .groupName("V2")
                .select()
                .apis(basePackage("com.algaworks.algafood.api"))
                .paths(ant("/v2/**"))
                .build()
                .useDefaultResponseMessages(false)
                .globalResponses(GET, globalGetResponseMessages())
                .globalResponses(POST, globalPostResponseMessages())
                .globalResponses(PUT, globalPutResponseMessages())
                .globalResponses(DELETE, globalDeleteResponseMessages())
                .additionalModels(typeResolver.resolve(Problem.class))
                .ignoredParameterTypes(ServletWebRequest.class,
                        URL.class, URI.class, URLStreamHandler.class, Resource.class,
                        File.class, InputStream.class)
                .directModelSubstitute(Pageable.class, PageableModelOpenAPI.class)
                .directModelSubstitute(Links.class, LinksModelOpenAPI.class)
                .alternateTypeRules(newRule(typeResolver.resolve(CollectionModel.class, CidadeModelVersionTwo.class),
                        CidadesModelVersionTwoOpenAPI.class))
                .alternateTypeRules(newRule(typeResolver.resolve(PagedModel.class, CozinhaModelVersionTwo.class),
                        CozinhasModelVersionTwoOpenAPI.class))
                .apiInfo(apiInfoVersionTwo())
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
                ref.key(k -> k.qualifiedModelName(q -> q.name("Problema")
                        .namespace("com.algaworks.algafood.api.exceptionhandler.model")))));
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("Algaworks", "https://www.algaworks.com", "contato@algaworks.com");

        return new ApiInfoBuilder()
                .title("AlgaFood API (Depreciada)")
                .description("API aberta para clientes e restaurantes.<br>"
                        + "<strong>Essa versao da API esta depreciada e deixara de existir a partir de 01/06/2023. "
                        + "Use a versao mais atual da API.")
                .version("1")
                .contact(contact)
                .build();
    }

    private ApiInfo apiInfoVersionTwo() {
        Contact contact = new Contact("Algaworks", "https://www.algaworks.com", "contato@algaworks.com");

        return new ApiInfoBuilder()
                .title("AlgaFood API")
                .description("API aberta para clientes e restaurantes")
                .version("2")
                .contact(contact)
                .build();
    }

}
