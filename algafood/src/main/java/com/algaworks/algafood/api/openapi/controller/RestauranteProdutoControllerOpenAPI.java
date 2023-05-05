package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenAPI {

    @ApiOperation("Lista os produtos de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do restaurante invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CollectionModel<ProdutoModel> listar(@ApiParam(value = "Codigo de um restaurante", required = true) Long id,
                                         @ApiParam(value = "Incluir inativos") Boolean incluirInativos);

    @ApiOperation("Busca um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do restaurante invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ProdutoModel buscar(@ApiParam(value = "Codigo de um restaurante", required = true) Long id,
                        @ApiParam(value = "Codigo de um produto", required = true) Long produtoID);

    @ApiOperation("Cadastra um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do restaurante invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "201", description = "Produto de um restaurante")
    })
    ProdutoModel adicionar(@ApiParam(value = "Codigo de um restaurante", required = true) Long id,
                           ProdutoInput produtoInput);

    @ApiOperation("Atualiza um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado"),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "400", description = "Codigo do restaurante invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ProdutoModel atualizar(@ApiParam(value = "Codigo de um restaurante", required = true) Long id,
                           @ApiParam(value = "Codigo de um produto", required = true) Long produtoID,
                           ProdutoInput produtoInput);

}
