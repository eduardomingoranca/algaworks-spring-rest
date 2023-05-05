package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteFormaPagamentoControllerOpenAPI {

    @ApiOperation("Lista as formas de pagamento associadas a restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do restaurante invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CollectionModel<FormaPagamentoModel> listar(@ApiParam(value = "Codigo de um restaurante", required = true) Long id);

    @ApiOperation("Desassociacao de restaurante com forma de pagamento")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do restaurante invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    void desassociar(@ApiParam(value = "Codigo de um restaurante", required = true) Long id,
                     @ApiParam(value = "Codigo da forma de pagamento", required = true) Long formaPagamentoID);

    @ApiOperation("Associacao de restaurante com forma de pagamento")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do restaurante invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    void associar(@ApiParam(value = "Codigo de um restaurante", required = true) Long id,
                  @ApiParam(value = "Codigo da forma de pagamento", required = true) Long formaPagamentoID);

}
