package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.model.input.FormaPagamentoInput;
import com.algaworks.algafood.api.v1.openapi.model.FormasPagamentoModelOpenAPI;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

@SecurityRequirement(name = "security_auth")
public interface FormaPagamentoControllerOpenAPI {

    @ApiResponses(@ApiResponse(responseCode = "200", description = "OK", content =
    @Content(mediaType = "application/json", schema = @Schema(implementation = FormasPagamentoModelOpenAPI.class))))
    ResponseEntity<CollectionModel<FormaPagamentoModel>> listar(ServletWebRequest request);

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da forma de pagamento invalida.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento nao encontrada", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<FormaPagamentoModel> buscar(Long id, ServletWebRequest request);


    @ApiResponses(@ApiResponse(responseCode = "201", description = "Forma de pagamento cadastrada"))
    FormaPagamentoModel adicionar(FormaPagamentoInput formaPagamentoInput);

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Forma de pagamento atualizada."),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento nao encontrada", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    FormaPagamentoModel atualizar(Long id, FormaPagamentoInput formaPagamentoInput);

}
