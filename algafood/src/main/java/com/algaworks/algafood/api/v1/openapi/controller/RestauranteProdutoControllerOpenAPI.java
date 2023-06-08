package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.input.ProdutoInput;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
public interface RestauranteProdutoControllerOpenAPI {

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do restaurante invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CollectionModel<ProdutoModel> listar(Long id, Boolean incluirInativos);

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do restaurante invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ProdutoModel buscar(Long id, Long produtoID);

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do restaurante invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "201", description = "Produto de um restaurante")
    })
    ProdutoModel adicionar(Long id, ProdutoInput produtoInput);

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado"),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "400", description = "Codigo do restaurante invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ProdutoModel atualizar(Long id, Long produtoID, ProdutoInput produtoInput);

}
