package com.algaworks.algafood.api.v2.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.v2.model.CidadeModelVersionTwo;
import com.algaworks.algafood.api.v2.model.input.CidadeInputVersionTwo;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;

public interface CidadeControllerVersionTwoOpenAPI {
    CollectionModel<CidadeModelVersionTwo> listar();

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da cidade invalida.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Cidade nao encontrada", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CidadeModelVersionTwo buscar(Long id);

    @ApiResponses(@ApiResponse(responseCode = "201", description = "Cidade cadastrada"))
    CidadeModelVersionTwo adicionar(CidadeInputVersionTwo cidadeInputVersionTwo);

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cidade atualizada."),
            @ApiResponse(responseCode = "404", description = "Cidade nao encontrada", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CidadeModelVersionTwo atualizar(Long id, CidadeInputVersionTwo cidadeInputVersionTwo);
}
