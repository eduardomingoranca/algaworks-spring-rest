package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

@Api(tags = "Formas de Pagamento")
public interface FormaPagamentoControllerOpenAPI {

    @ApiOperation("Lista as formas de pagamento")
    ResponseEntity<List<FormaPagamentoModel>> listar(ServletWebRequest request);

    @ApiOperation("Busca uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da forma de pagamento invalida.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento nao encontrada", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<FormaPagamentoModel> buscar(@ApiParam(value = "ID de uma forma de pagamento", required = true) Long id,
                                               ServletWebRequest request);


    @ApiOperation("Cadastra uma forma de pagamento")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "Forma de pagamento cadastrada"))
    FormaPagamentoModel adicionar(@ApiParam(name = "corpo", value = "Representacao de uma nova forma de pagamento",
            required = true)
                                  FormaPagamentoInput formaPagamentoInput);

    @ApiOperation("Atualiza uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Forma de pagamento atualizada."),
            @ApiResponse(responseCode = "404", description = "Forma de pagamento nao encontrada", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    FormaPagamentoModel atualizar(@ApiParam(value = "ID de uma forma de pagamento", required = true) Long id,
                                  @ApiParam(name = "corpo", value = "Representacao de uma nova forma de pagamento",
                                          required = true)
                                  FormaPagamentoInput formaPagamentoInput);

}
